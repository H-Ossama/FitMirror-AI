package com.fitmirror.ai.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.fitmirror.ai.domain.model.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = DeepPurple,           // Ink
    onPrimary = PureWhite,
    primaryContainer = PureWhite,
    onPrimaryContainer = DarkCharcoal,
    secondary = ElectricBlue,       // Terracotta accent
    onSecondary = PureWhite,
    secondaryContainer = SoftWhite,
    onSecondaryContainer = DarkCharcoal,
    background = SoftWhite,         // Warm ivory
    onBackground = DarkCharcoal,
    surface = PureWhite,
    onSurface = DarkCharcoal,
    surfaceVariant = LightGray,
    onSurfaceVariant = MediumGray,
    outline = LightGray,
    outlineVariant = LightGray.copy(alpha = 0.6f),
    error = ErrorRed,
    onError = PureWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = NightIvory,           // Warm off-white
    onPrimary = Night,
    primaryContainer = NightSurface,
    onPrimaryContainer = NightIvory,
    secondary = ElectricBlueAccent, // Soft terracotta
    onSecondary = Night,
    secondaryContainer = NightSurface,
    onSecondaryContainer = NightIvory,
    background = Night,
    onBackground = NightIvory,
    surface = NightSurface,
    onSurface = NightIvory,
    surfaceVariant = NightHairline,
    onSurfaceVariant = MediumGray,
    outline = NightHairline,
    outlineVariant = NightHairline.copy(alpha = 0.6f),
    error = ErrorRed,
    onError = NightIvory
)

@Composable
fun FitMirrorTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LuxuryTypography,
        shapes = LuxuryShapes,
        content = content
    )
}
