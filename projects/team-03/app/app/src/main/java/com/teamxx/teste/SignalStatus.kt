package com.teamxx.teste

import androidx.compose.ui.graphics.Color

/**
 * Enumeração [SignalStatus]: representa cada um dos 4 possíveis estados da medição de sinal Wi-Fi.
 * Encapsula o valor numérico em dBm, as propriedades visuais e a recomendação textual.
 *
 * @param label Texto exibido no chip de status e na tela de detalhes (ex: "Aguardando", "Sinal Forte").
 * @param dbm Valor numérico simulado em dBm (ou null quando nenhuma medição foi realizada).
 * @param sectorColor Cor de iluminação do setor do radar em Canvas.
 * @param textColor Cor temática associada ao nível de sinal (usada em textos, chips e ícones).
 * @param recommendation Texto orientador com diagnóstico prático exibido na tela de detalhes.
 */
enum class SignalStatus(
    val label: String,
    val dbm: Int?,
    val sectorColor: Color,
    val textColor: Color,
    val recommendation: String
) {
    // Estado inicial: nenhuma medição realizada
    NONE(
        label = "Aguardando",
        dbm = null,
        sectorColor = Color.Transparent,
        textColor = Color(0xFF8FA3B8),
        recommendation = "Nenhuma verificação realizada. Toque no botão 'Iniciar Verificação' na tela inicial para medir o sinal."
    ),

    // Estado de Sinal Forte (-45 dBm): excelente qualidade
    STRONG(
        label = "Sinal Forte",
        dbm = -45,
        sectorColor = Color(0xFF4CAF50),
        textColor = Color(0xFF81C784),
        recommendation = "Excelente conexão! Ideal para streaming 4K, jogos online com baixa latência e chamadas de vídeo em alta definição."
    ),

    // Estado de Sinal Médio (-68 dBm): qualidade aceitável
    MEDIUM(
        label = "Sinal Médio",
        dbm = -68,
        sectorColor = Color(0xFFFF9800),
        textColor = Color(0xFFFFB74D),
        recommendation = "Sinal utilizável, mas pode oscilar em horários de pico. Bom para navegação comum, redes sociais e vídeos."
    ),

    // Estado de Sinal Fraco (-85 dBm): baixa qualidade, sujeito a quedas
    WEAK(
        label = "Sinal Fraco",
        dbm = -85,
        sectorColor = Color(0xFFE53935),
        textColor = Color(0xFFFF5252),
        recommendation = "Sinal fraco com alto risco de desconexões. Recomendado aproximar-se do roteador ou utilizar um repetidor de sinal."
    );

    companion object {
        /**
         * Converte com segurança uma String contendo o nome do status no enum [SignalStatus].
         * Retorna [NONE] caso o valor seja nulo ou inválido.
         */
        fun fromName(name: String?): SignalStatus {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: NONE
        }
    }
}
