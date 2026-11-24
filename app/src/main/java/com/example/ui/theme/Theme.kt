package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = OrchidGlow,
    onPrimary = Color.White,
    primaryContainer = DarkPlumContainer,
    onPrimaryContainer = LightLavenderText,
    secondary = WarmGold,
    onSecondary = DeepInk,
    tertiary = LoveGradientEnd,
    background = DarkSpaceBg,
    onBackground = LightLavenderText,
    surface = DarkPlumSurface,
    onSurface = LightLavenderText,
    surfaceVariant = DarkPlumContainer,
    onSurfaceVariant = SoftLilacText,
    outline = Color(0xFF4B3A6B)
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalPurple,
    onPrimary = Color.White,
    primaryContainer = SoftLavenderSurface,
    onPrimaryContainer = DeepPlum,
    secondary = WarmGold,
    onSecondary = DeepInk,
    tertiary = LoveGradientStart,
    onTertiary = Color.White,
    background = SoftLavenderBg,
    onBackground = DeepInk,
    surface = Color.White,
    onSurface = DeepInk,
    surfaceVariant = SoftLavenderSurface,
    onSurfaceVariant = MutedPlumText,
    outline = LavenderOutline
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp)
)

@Composable
fun YashShayariTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
