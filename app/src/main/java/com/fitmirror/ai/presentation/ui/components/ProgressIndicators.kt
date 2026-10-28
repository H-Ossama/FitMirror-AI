package com.fitmirror.ai.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.ElectricBlueMuted
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import kotlinx.coroutines.delay

@Composable
fun AnimatedStatusCycler(
    statuses: List<String> = listOf(
        "Finding body...",
        "Analyzing clothes...",
        "Matching pose...",
        "Generating..."
    ),
    modifier: Modifier = Modifier,
    cycleDurationMillis: Long = 1200L
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(cycleDurationMillis)
            currentIndex = (currentIndex + 1) % statuses.size
        }
    }

    AnimatedContent(
        targetState = statuses[currentIndex],
        transitionSpec = {
            fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
        },
        label = "statusTextTransition",
        modifier = modifier
    ) { status ->
        Text(
            text = status,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
fun LuxuryProgressBar(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    showPercentage: Boolean = true
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = spring(),
        label = "progressBar"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        if (showPercentage) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "AI Garment Synthesis",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(PillShape)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(8.dp)
                    .clip(PillShape)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                DeepPurple,
                                ElectricBlueMuted
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0 until pageCount) {
            val isSelected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 28.dp else 8.dp,
                animationSpec = spring(),
                label = "indicatorWidth"
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) DeepPurple else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                label = "indicatorColor"
            )

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )

            if (index < pageCount - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
