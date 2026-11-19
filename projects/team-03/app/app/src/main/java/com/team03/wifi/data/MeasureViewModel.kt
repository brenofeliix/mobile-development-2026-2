package com.team03.wifi.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.team03.wifi.SignalStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

// Estados da medição que a tela mostra (Sprint 06).
sealed interface MeasureState {
    data object Idle : MeasureState
    data object Loading : MeasureState
    data class Success(val dbm: Int, val status: SignalStatus) : MeasureState
    data class Error(val message: String) : MeasureState
}

class MeasureViewModel(app: Application) : AndroidViewModel(app) {

    private val reader = WifiReader(app)

    private val _state = MutableStateFlow<MeasureState>(MeasureState.Idle)
    val state: StateFlow<MeasureState> = _state

    // onSuccess é chamado só quando a medição dá certo (usado para salvar no banco).
    fun measure(onSuccess: (SignalStatus) -> Unit) {
        if (_state.value is MeasureState.Loading) return

        viewModelScope.launch {
            _state.value = MeasureState.Loading

            val result = try {
                medir()
            } catch (e: Exception) {
                MeasureState.Error("Não foi possível ler o sinal do Wi-Fi.")
            }
            _state.value = result

            if (result is MeasureState.Success) onSuccess(result.status)
        }
    }

    // 3 leituras com 0,5 s entre elas; o resultado é a média (o sinal oscila).
    private suspend fun medir(): MeasureState {
        val amostras = mutableListOf<Int>()

        repeat(3) { i ->
            when (val leitura = withContext(Dispatchers.IO) { reader.read() }) {
                is WifiReading.Success -> amostras.add(leitura.dbm)
                WifiReading.WifiOff ->
                    return MeasureState.Error("Wi-Fi desligado. Ative o Wi-Fi para medir.")
                WifiReading.NotConnected ->
                    return MeasureState.Error("Sem conexão Wi-Fi. Conecte-se a uma rede para medir.")
            }
            if (i < 2) delay(500)
        }

        val dbm = amostras.average().roundToInt()
        return MeasureState.Success(dbm, classificar(dbm))
    }

    private fun classificar(dbm: Int): SignalStatus = when {
        dbm >= -60 -> SignalStatus.STRONG
        dbm >= -75 -> SignalStatus.MEDIUM
        else -> SignalStatus.WEAK
    }
}