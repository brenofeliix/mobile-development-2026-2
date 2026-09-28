// Pacote onde estão localizadas as configurações de tema e tipografia
package com.teamxx.teste.ui.theme

// Importa a classe Typography do Material 3 para agrupar estilos de texto
import androidx.compose.material3.Typography

// Importa a classe TextStyle que permite configurar tamanho, peso e espaçamento de fonte
import androidx.compose.ui.text.TextStyle

// Importa FontFamily para definir a família de fontes (padrão do sistema Android, Roboto)
import androidx.compose.ui.text.font.FontFamily

// Importa FontWeight para definir a espessura da fonte (fina, normal, média, negrito)
import androidx.compose.ui.text.font.FontWeight

// Importa a unidade 'sp' (Scale-independent Pixels), recomendada para fontes no Android
import androidx.compose.ui.unit.sp

// ========================================================================================
// CONFIGURAÇÃO DA ESCALA TIPOGRÁFICA (Typography)
// Define os estilos de texto padrão que serão herdados pelos componentes Material 3
// ========================================================================================
val Typography = Typography(
    // bodyLarge: estilo tipográfico padrão para textos de corpo e parágrafos normais
    bodyLarge = TextStyle(
        // Utiliza a família de fontes padrão do sistema Android (Roboto)
        fontFamily = FontFamily.Default,
        // Define o peso da fonte como normal (não é negrito nem itálico)
        fontWeight = FontWeight.Normal,
        // Define o tamanho da fonte em 16 sp (escala com a acessibilidade do usuário)
        fontSize = 16.sp,
        // Define a altura de cada linha em 24 sp para facilitar a leitura
        lineHeight = 24.sp,
        // Define um leve espaçamento de 0.5 sp entre as letras
        letterSpacing = 0.5.sp
    )
)