package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = VerdePrincipal,
    secondary = Laranja,
    tertiary = VerdeClaro,
    background = TextoPrincipal,
    surface = TextoPrincipal,
    onPrimary = Branco,
    onBackground = Branco,
    onSurface = Branco,
    error = Vermelho
)

private val LightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    secondary = Laranja,
    tertiary = VerdeEscuro,
    background = Fundo,
    surface = Branco,
    onPrimary = Branco,
    onSecondary = Branco,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal,
    error = Vermelho
)
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
