package com.testapp.presentation.components.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Primary02 = Color(0xFFE5EBEF)
val BackgroundLight = Color(0xFFFFFFFF)
val Primary05 = Color(0xFF444E72)
val onSer = Color(0xFF838BAA)
val gradientStart = Color(0xD4002762)
val gradientEnd = Color(0xB3002762)
val backgroundClose = Color(0xFFE5E5E5)

//dark theme colors
val DarkColorPalette = darkColors(
    primary = Primary05,
    background = BackgroundLight,
    onPrimary = Primary02,
    onSurface = onSer
)

//light theme colors
val LightColorPalette = lightColors(
    primary = Primary05,
    background = BackgroundLight,
    onPrimary = Primary02,
    onSurface = onSer
)

