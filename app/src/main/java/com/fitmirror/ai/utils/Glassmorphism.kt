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

/**
 * Applies Apple Liquid Glass inspired luxury glassmorphic card styling.
 * Soft translucent background with subtle shimmering border gradient.
 */
@Composable
fun Modifier.glassmorphic(
    shape: Shape,
    borderWidth: Dp = 1.dp,
    customAlpha: Float? = null
): Modifier = composed {
    val darkTheme = isSystemInDarkTheme()
    val surfaceColor = if (darkTheme) {
        Color(0xFF16161D).copy(alpha = customAlpha ?: 0.75f)
    } else {
        Color(0xFFFFFFFF).copy(alpha = customAlpha ?: 0.85f)
    }
    val borderColors = if (darkTheme) {
        listOf(
            Color(0xFF8A2BE2).copy(alpha = 0.45f),
            Color(0xFF00F0FF).copy(alpha = 0.25f),
            Color(0xFFFFFFFF).copy(alpha = 0.08f)
        )
    } else {
        listOf(
            Color(0xFFFFFFFF).copy(alpha = 0.95f),
            Color(0xFF3E1B6B).copy(alpha = 0.22f),
            Color(0xFF00F0FF).copy(alpha = 0.15f)
        )
    }

    this
        .clip(shape)
        .background(surfaceColor)
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(borderColors),
            shape = shape
        )
}

/**
 * Luxury gradient background brush for splash & hero sections.
 */
@Composable
fun luxuryHeroGradient(): Brush {
    val darkTheme = isSystemInDarkTheme()
    return if (darkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0B0B0F),
                Color(0xFF170E28),
                Color(0xFF0D0D14)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF9FAFF),
                Color(0xFFF3EEFC),
                Color(0xFFEEF5FF)
            )
        )
    }
}
