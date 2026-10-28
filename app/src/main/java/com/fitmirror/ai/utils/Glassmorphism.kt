package com.fitmirror.ai.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.theme.Hairline
import com.fitmirror.ai.presentation.ui.theme.NightHairline
import com.fitmirror.ai.presentation.ui.theme.NightSurface
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.ui.theme.SoftWhite

/**
 * Editorial minimal surface: solid warm-white (or warm-dark) card
 * with a single hairline border. Replaces the former neon glass look.
 */
@Composable
fun Modifier.glassmorphic(
    shape: Shape,
    borderWidth: Dp = 1.dp,
    customAlpha: Float? = null
): Modifier = composed {
    val darkTheme = isSystemInDarkTheme()
    val surfaceColor = if (darkTheme) {
        NightSurface.copy(alpha = customAlpha ?: 0.98f)
    } else {
        PureWhite.copy(alpha = customAlpha ?: 0.96f)
    }
    val borderColor = if (darkTheme) NightHairline else Hairline

    this
        .clip(shape)
        .background(surfaceColor)
        .border(
            width = borderWidth,
            color = borderColor,
            shape = shape
        )
}

/**
 * Soft editorial background gradient — warm ivory fading to white.
 */
@Composable
fun luxuryHeroGradient(): Brush {
    val darkTheme = isSystemInDarkTheme()
    return if (darkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF121110),
                Color(0xFF1C1A16)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                SoftWhite,
                Color(0xFFFAF9F6),
                PureWhite
            )
        )
    }
}
