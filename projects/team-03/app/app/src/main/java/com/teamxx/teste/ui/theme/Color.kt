// Define o pacote onde este arquivo de cores está localizado no projeto
package com.teamxx.teste.ui.theme

// Importa a classe Color do Jetpack Compose para criar e manipular cores
import androidx.compose.ui.graphics.Color

// ========================================================================================
// CORES PADRÃO DO MATERIAL THEME (Paleta Base)
// O formato 0xFF significa: canal Alpha (opacidade total = FF) seguido de R, G, B em hexadecimal
// ========================================================================================

// Roxo claro utilizado como cor primária no modo escuro padrão
val Purple80 = Color(0xFFD0BCFF)

// Roxo acinzentado claro utilizado como cor secundária no modo escuro padrão
val PurpleGrey80 = Color(0xFFCCC2DC)

// Rosa suave utilizado como cor terciária no modo escuro padrão
val Pink80 = Color(0xFFEFB8C8)

// Roxo escuro utilizado como cor primária no modo claro padrão
val Purple40 = Color(0xFF6650a4)

// Roxo acinzentado escuro utilizado como cor secundária no modo claro padrão
val PurpleGrey40 = Color(0xFF625b71)

// Rosa escuro utilizado como cor terciária no modo claro padrão
val Pink40 = Color(0xFF7D5260)

// ========================================================================================
// CORES CUSTOMIZADAS DO WI-FI MAPPER (Identidade Visual Dark Mode)
// ========================================================================================

// Cor de fundo principal da tela: azul marinho quase preto (#0B111E)
val DarkBackground = Color(0xFF0B111E)

// Cor de fundo do card circular do cabeçalho que envolve o ícone de Wi-Fi (#142033)
val HeaderCardBackground = Color(0xFF142033)

// Cor da borda sutil ao redor do card circular do cabeçalho (#1E304B)
val BorderColor = Color(0xFF1E304B)

// Cor ciano vibrante usada no botão principal "Iniciar Verificação" e no ícone do cabeçalho (#00E5FF)
val CyanPrimary = Color(0xFF00E5FF)

// Cor ciano suave usada no subtítulo/slogan abaixo do título (#00D2E5)
val CyanSubtitle = Color(0xFF00D2E5)

// Cor azul escuro usada para desenhar os círculos e retas de mira do Radar (#1E2D44)
val GridLinesColor = Color(0xFF1E2D44)

// Cor cinza azulado claro para textos secundários, rótulo "DBM" e status "Aguardando" (#8FA3B8)
val TextMuted = Color(0xFF8FA3B8)