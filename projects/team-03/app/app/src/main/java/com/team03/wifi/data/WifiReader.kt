package com.team03.wifi.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager

// Valor que o Android devolve quando não há conexão Wi-Fi ativa
private const val INVALID_RSSI = -127

// Resultado de UMA leitura do Wi-Fi do aparelho.
sealed interface WifiReading {
    data class Success(val dbm: Int) : WifiReading
    data object WifiOff : WifiReading       // Wi-Fi desligado
    data object NotConnected : WifiReading  // ligado, mas sem rede Wi-Fi conectada
}

// Fronteira com o Android: só esta classe fala com o WifiManager.
class WifiReader(context: Context) {

    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun read(): WifiReading {
        if (!wifiManager.isWifiEnabled) return WifiReading.WifiOff

        // A rede ativa precisa ser Wi-Fi (e não dados móveis, por exemplo)
        val capacidades = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capacidades == null || !capacidades.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return WifiReading.NotConnected
        }

        @Suppress("DEPRECATION")
        val rssi = wifiManager.connectionInfo?.rssi ?: return WifiReading.NotConnected

        // -127 = não há conexão ativa
        if (rssi <= INVALID_RSSI) return WifiReading.NotConnected

        return WifiReading.Success(rssi)
    }
}