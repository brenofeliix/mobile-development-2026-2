package com.team05.sarc.notificacao

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Sprint 06: tudo sobre a notificação fica neste arquivo.
object NotificacaoSarc {
    private const val CANAL = "acionamentos_sarc" // identificador interno do canal

    // FR-01: cria o canal "Acionamentos SARC" (só existe a partir do Android 8; antes não é preciso).
    fun criarCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(CANAL, "Acionamentos SARC", NotificationManager.IMPORTANCE_HIGH)
            context.getSystemService(NotificationManager::class.java).createNotificationChannel(canal)
        }
    }

    // FR-02 a FR-04: devolve true se a notificação saiu e false se está bloqueada.
    fun enviar(context: Context, mensagem: String): Boolean {
        val gerenciador = NotificationManagerCompat.from(context)
        if (!gerenciador.areNotificationsEnabled()) return false // permissão negada ou desligada nas Configurações

        // FR-03: tocar na notificação abre o SARC.
        val abrir = PendingIntent.getActivity(
            context, 0,
            context.packageManager.getLaunchIntentForPackage(context.packageName),
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificacao = NotificationCompat.Builder(context, CANAL)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("SARC — Acionamento registrado")
            .setContentText(mensagem)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensagem)) // mostra a mensagem inteira ao expandir
            .setContentIntent(abrir)
            .setAutoCancel(true) // some ao tocar
            .build()

        return try {
            gerenciador.notify(System.currentTimeMillis().toInt(), notificacao) // id diferente: várias ficam na barra
            true
        } catch (e: SecurityException) { // defesa extra: se o Android recusar, o app não trava
            false
        }
    }
}
