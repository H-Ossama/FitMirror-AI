package com.fitmirror.ai.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.utils.glassmorphic
import com.fitmirror.ai.utils.pressScale

@Composable
fun LuxuryCard(
    modifier: Modifier = Modifier,
    shape: Shape = GlassCardShape,
    borderWidth: Dp = 1.dp,
    elevation: Dp = 8.dp,
    onClick: (() -> Unit)? = null,
    contentPadding: Dp = LuxuryDimensions.cardPadding,
    content: @Composable BoxScope.() -> Unit
) {
    val baseModifier = modifier
        .shadow(
            elevation = elevation,
            shape = shape,
            ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        )
        .glassmorphic(shape = shape, borderWidth = borderWidth)

    val interactiveModifier = if (onClick != null) {
        baseModifier.pressScale(minScale = 0.98f, onClick = onClick)
    } else {
        baseModifier
    }

    Box(
        modifier = interactiveModifier.padding(contentPadding),
        content = content
    )
}

@Composable
fun ElevatedLuxuryCard(
    modifier: Modifier = Modifier,
    shape: Shape = GlassCardShape,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    LuxuryCard(
        modifier = modifier,
        shape = shape,
        borderWidth = 1.2.dp,
        elevation = 14.dp,
        onClick = onClick,
        content = content
    )
}
