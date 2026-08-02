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
    primary = DeepPurple,
    onPrimary = PureWhite,
    primaryContainer = SoftWhite,
    onPrimaryContainer = DeepPurpleDark,
    secondary = ElectricBlueAccent,
    onSecondary = PureWhite,
    secondaryContainer = LightGray,
    onSecondaryContainer = DarkCharcoal,
    background = PureWhite,
    onBackground = DarkCharcoal,
    surface = SoftWhite,
    onSurface = DarkCharcoal,
    surfaceVariant = LightGray,
    onSurfaceVariant = DarkCharcoal,
    error = ErrorRed,
    onError = PureWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = DeepPurpleLight,
    onPrimary = PureWhite,
    primaryContainer = LuxuryDarkCard,
    onPrimaryContainer = PureWhite,
    secondary = ElectricBlue,
    onSecondary = PureBlack,
    secondaryContainer = DeepPurple,
    onSecondaryContainer = PureWhite,
    background = PureBlack,
    onBackground = PureWhite,
    surface = LuxuryDarkBackground,
    onSurface = PureWhite,
    surfaceVariant = LuxuryDarkCard,
    onSurfaceVariant = LightGray,
    error = ErrorRed,
    onError = PureWhite
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
