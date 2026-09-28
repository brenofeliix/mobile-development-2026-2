// Pacote onde o tema visual do aplicativo reside
package com.teamxx.teste.ui.theme

// Importa a classe Activity base do Android
import android.app.Activity
// Importa Build para verificar a versão da API do Android em tempo de execução
import android.os.Build
// Importa a função do Compose que detecta se o celular do usuário está no modo noturno
import androidx.compose.foundation.isSystemInDarkTheme
// Importa o componente base MaterialTheme que envelopa os filhos com cores e fontes
import androidx.compose.material3.MaterialTheme
// Importa a função que cria um esquema de cores para o modo escuro
import androidx.compose.material3.darkColorScheme
// Importa a função para extrair cores dinâmicas escuras do papel de parede (Android 12+)
import androidx.compose.material3.dynamicDarkColorScheme
// Importa a função para extrair cores dinâmicas claras do papel de parede (Android 12+)
import androidx.compose.material3.dynamicLightColorScheme
// Importa a função que cria um esquema de cores para o modo claro
import androidx.compose.material3.lightColorScheme
// Importa a anotação @Composable para indicar que a função desenha elementos na tela
import androidx.compose.runtime.Composable
// Importa LocalContext para obter o Context do Android dentro de um Composable
import androidx.compose.ui.platform.LocalContext

// ========================================================================================
// ESQUEMA DE CORES PARA O MODO ESCURO (Dark Mode)
// ========================================================================================
private val DarkColorScheme = darkColorScheme(
    // Cor primária usada em elementos principais quando no modo escuro
    primary = Purple80,
    // Cor secundária usada em elementos de apoio quando no modo escuro
    secondary = PurpleGrey80,
    // Cor terciária usada para contraste ou detalhes no modo escuro
    tertiary = Pink80
)

// ========================================================================================
// ESQUEMA DE CORES PARA O MODO CLARO (Light Mode)
// ========================================================================================
private val LightColorScheme = lightColorScheme(
    // Cor primária usada em elementos principais quando no modo claro
    primary = Purple40,
    // Cor secundária usada em elementos de apoio quando no modo claro
    secondary = PurpleGrey40,
    // Cor terciária usada para contraste ou detalhes no modo claro
    tertiary = Pink40
)

// ========================================================================================
// FUNÇÃO DO TEMA PRINCIPAL (TesteTheme)
// ========================================================================================

/**
 * Função Composable que envolve a aplicação inteira para fornecer cores, fontes e formas.
 *
 * @param darkTheme Define se o app deve rodar em modo noturno (padrão: segue o celular)
 * @param dynamicColor Se deve usar as cores dinâmicas do papel de parede no Android 12+
 * @param content O bloco de código que contém as telas e botões que herdarão o tema
 */
@Composable
fun TesteTheme(
    // Parâmetro darkTheme: chama isSystemInDarkTheme() para saber se o sistema está em modo escuro
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Parâmetro dynamicColor: padrão como true (ativa cores do Material You no Android 12)
    dynamicColor: Boolean = true,
    // Parâmetro content: função lambda que recebe o conteúdo visual do aplicativo
    content: @Composable () -> Unit
) {
    // Escolhe dinamicamente qual esquema de cores será aplicado na tela
    val colorScheme = when {
        // Se dynamicColor estiver ativado e a versão do Android for Android 12 ou superior (API 31 / S):
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // Obtém o Context atual do Android através do LocalContext do Compose
            val context = LocalContext.current
            // Se o usuário estiver no modo escuro, extrai cores escuras; senão, cores claras
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        // Se o dispositivo estiver configurado em modo escuro pelo usuário:
        darkTheme -> DarkColorScheme

        // Em qualquer outro cenário (modo claro padrão):
        else -> LightColorScheme
    }

    // MaterialTheme: Aplica a configuração de cores e tipografia em cascata para todos os filhos
    MaterialTheme(
        // Passa o esquema de cores calculado acima
        colorScheme = colorScheme,
        // Passa os estilos de fonte definidos no arquivo Type.kt
        typography = Typography,
        // Executa e renderiza os elementos filhos que estão dentro do tema
        content = content
    )
}