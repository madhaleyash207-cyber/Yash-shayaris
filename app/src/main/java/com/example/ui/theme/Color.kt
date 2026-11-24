package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.data.model.ShayariCardTheme
import com.example.data.model.ShayariCategory

// Brand Purple & Accent Palette
val RoyalPurple = Color(0xFF5E2CA5)
val DeepPlum = Color(0xFF3E1878)
val VibrantViolet = Color(0xFF7B3FE4)
val OrchidGlow = Color(0xFF9E5CF6)
val SoftLavenderBg = Color(0xFFF8F5FF)
val SoftLavenderSurface = Color(0xFFF2ECFF)
val LavenderOutline = Color(0xFFD9C8F5)
val WarmGold = Color(0xFFFFC857)
val DeepInk = Color(0xFF1A122B)
val MutedPlumText = Color(0xFF5D4F78)

// Dark Mode Tokens
val DarkSpaceBg = Color(0xFF120C1F)
val DarkPlumSurface = Color(0xFF1E1533)
val DarkPlumContainer = Color(0xFF2B1E47)
val LightLavenderText = Color(0xFFF2ECFF)
val SoftLilacText = Color(0xFFCBBCE8)

// Category & Card Theme Gradients
val LoveGradientStart = Color(0xFFD81B60)
val LoveGradientEnd = Color(0xFFFF6B6B)
val LoveTintBg = Color(0xFFFFF0F5)

val SadGradientStart = Color(0xFF283593)
val SadGradientEnd = Color(0xFF5C6BC0)
val SadTintBg = Color(0xFFEEF1FF)

val DostiGradientStart = Color(0xFF00796B)
val DostiGradientEnd = Color(0xFF26A69A)
val DostiTintBg = Color(0xFFE8FAF6)

val AttitudeGradientStart = Color(0xFFD84315)
val AttitudeGradientEnd = Color(0xFFFF9800)
val AttitudeTintBg = Color(0xFFFFF4E5)

val RoyalGradientStart = Color(0xFF5E2CA5)
val RoyalGradientEnd = Color(0xFF9C4DCC)
val RoyalTintBg = Color(0xFFF3EBFF)

val FavoriteHeartPink = Color(0xFFFF3B6B)

data class CardPalette(
    val gradientStart: Color,
    val gradientEnd: Color,
    val accentTint: Color,
    val badgeColor: Color
)

fun categoryPalette(category: ShayariCategory): CardPalette = when (category) {
    ShayariCategory.LOVE -> CardPalette(LoveGradientStart, LoveGradientEnd, LoveTintBg, LoveGradientStart)
    ShayariCategory.SAD -> CardPalette(SadGradientStart, SadGradientEnd, SadTintBg, SadGradientStart)
    ShayariCategory.DOSTI -> CardPalette(DostiGradientStart, DostiGradientEnd, DostiTintBg, DostiGradientStart)
    ShayariCategory.ATTITUDE -> CardPalette(AttitudeGradientStart, AttitudeGradientEnd, AttitudeTintBg, AttitudeGradientStart)
    ShayariCategory.CUSTOM -> CardPalette(RoyalGradientStart, RoyalGradientEnd, RoyalTintBg, RoyalGradientStart)
}

fun cardThemePalette(theme: ShayariCardTheme): CardPalette = when (theme) {
    ShayariCardTheme.ROYAL_PURPLE -> CardPalette(RoyalGradientStart, RoyalGradientEnd, RoyalTintBg, RoyalGradientStart)
    ShayariCardTheme.ROSE_LOVE -> CardPalette(LoveGradientStart, LoveGradientEnd, LoveTintBg, LoveGradientStart)
    ShayariCardTheme.INDIGO_SAD -> CardPalette(SadGradientStart, SadGradientEnd, SadTintBg, SadGradientStart)
    ShayariCardTheme.EMERALD_DOSTI -> CardPalette(DostiGradientStart, DostiGradientEnd, DostiTintBg, DostiGradientStart)
    ShayariCardTheme.AMBER_ATTITUDE -> CardPalette(AttitudeGradientStart, AttitudeGradientEnd, AttitudeTintBg, AttitudeGradientStart)
}
