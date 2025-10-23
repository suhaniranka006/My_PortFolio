package com.suhani.portfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
    primary = DarkPink,
    onPrimary = White,
    secondary = LightPink,
    onSecondary = Black,
    background = DarkPink.copy(alpha = 0.9f),
    onBackground = White,
    surface = DarkPink,
    onSurface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = LightPink,
    onPrimary = White,
    secondary = DarkPink,
    onSecondary = White,
    background = LightPink.copy(alpha = 0.3f),
    onBackground = Black,
    surface = White,
    onSurface = DarkPink,
)

@Composable
fun MyPortfolioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
