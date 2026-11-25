package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class NoorExtendedColors(
    val darkGreen: Color,
    val deepEmerald: Color,
    val midGreen: Color,
    val gold: Color,
    val brightGold: Color,
    val goldSoft: Color,
    val cardSurface: Color,
    val subtleSurface: Color,
    val borderColor: Color,
    val mutedText: Color,
    val success: Color,
    val rose: Color,
    val isDark: Boolean
)

val LocalNoorColors = staticCompositionLocalOf {
    NoorExtendedColors(
        darkGreen = NoorDarkGreen,
        deepEmerald = NoorDeepEmerald,
        midGreen = NoorMidGreen,
        gold = NoorGold,
        brightGold = NoorBrightGold,
        goldSoft = NoorGoldSoft,
        cardSurface = NoorWhite,
        subtleSurface = NoorMintSurface,
        borderColor = NoorDividerLight,
        mutedText = NoorTextMuted,
        success = NoorSuccessGreen,
        rose = NoorRoseAccent,
        isDark = false
    )
}

private val LightColorScheme = lightColorScheme(
    primary = NoorDarkGreen,
    onPrimary = NoorWhite,
    primaryContainer = NoorSoftGreenCard,
    onPrimaryContainer = NoorDarkGreen,
    secondary = NoorGold,
    onSecondary = NoorDarkGreen,
    secondaryContainer = NoorGoldSoft,
    onSecondaryContainer = NoorDeepGold,
    tertiary = NoorMidGreen,
    onTertiary = NoorWhite,
    background = NoorOffWhite,
    onBackground = NoorTextDark,
    surface = NoorWhite,
    onSurface = NoorTextDark,
    surfaceVariant = NoorMintSurface,
    onSurfaceVariant = NoorTextMuted,
    outline = NoorDividerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = NoorGold,
    onPrimary = NoorDarkGreen,
    primaryContainer = NoorDarkCard,
    onPrimaryContainer = NoorBrightGold,
    secondary = NoorBrightGold,
    onSecondary = NoorDarkGreen,
    secondaryContainer = NoorDarkGreen,
    onSecondaryContainer = NoorGoldSoft,
    tertiary = NoorMidGreen,
    onTertiary = NoorWhite,
    background = NoorDarkBg,
    onBackground = NoorTextLight,
    surface = NoorDarkSurface,
    onSurface = NoorTextLight,
    surfaceVariant = NoorDarkCard,
    onSurfaceVariant = NoorTextLightMuted,
    outline = NoorDarkDivider
)

val NoorShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun NoorTheme(
    darkTheme: Boolean = false,
    fontScale: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extended = if (darkTheme) {
        NoorExtendedColors(
            darkGreen = NoorDarkGreen,
            deepEmerald = NoorDeepEmerald,
            midGreen = NoorMidGreen,
            gold = NoorGold,
            brightGold = NoorBrightGold,
            goldSoft = Color(0xFF282211),
            cardSurface = NoorDarkCard,
            subtleSurface = NoorDarkSurface,
            borderColor = NoorDarkDivider,
            mutedText = NoorTextLightMuted,
            success = Color(0xFF34A86B),
            rose = Color(0xFFE56B78),
            isDark = true
        )
    } else {
        NoorExtendedColors(
            darkGreen = NoorDarkGreen,
            deepEmerald = NoorDeepEmerald,
            midGreen = NoorMidGreen,
            gold = NoorGold,
            brightGold = NoorBrightGold,
            goldSoft = NoorGoldSoft,
            cardSurface = NoorWhite,
            subtleSurface = NoorMintSurface,
            borderColor = NoorDividerLight,
            mutedText = NoorTextMuted,
            success = NoorSuccessGreen,
            rose = NoorRoseAccent,
            isDark = false
        )
    }

    CompositionLocalProvider(
        LocalNoorColors provides extended
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = buildNoorTypography(fontScale),
            shapes = NoorShapes,
            content = content
        )
    }
}
