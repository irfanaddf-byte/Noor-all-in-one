package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val AmiriFontFamily = FontFamily(
    Font(R.font.amiri, FontWeight.Normal)
)

val PlayfairFontFamily = FontFamily(
    Font(R.font.playfair_display, FontWeight.Bold)
)

val JakartaFontFamily = FontFamily(
    Font(R.font.plus_jakarta_sans, FontWeight.Normal)
)

fun buildNoorTypography(fontScale: Float = 1.0f): Typography {
    val s = fontScale.coerceIn(0.85f, 1.35f)
    return Typography(
        displayLarge = TextStyle(
            fontFamily = PlayfairFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (32 * s).sp,
            lineHeight = (38 * s).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = PlayfairFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (24 * s).sp,
            lineHeight = (30 * s).sp
        ),
        titleLarge = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (20 * s).sp,
            lineHeight = (26 * s).sp
        ),
        titleMedium = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (16 * s).sp,
            lineHeight = (22 * s).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (16 * s).sp,
            lineHeight = (24 * s).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * s).sp,
            lineHeight = (20 * s).sp
        ),
        labelLarge = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (14 * s).sp,
            lineHeight = (18 * s).sp
        ),
        labelSmall = TextStyle(
            fontFamily = JakartaFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (11 * s).sp,
            lineHeight = (14 * s).sp
        )
    )
}

val Typography = buildNoorTypography(1.0f)
