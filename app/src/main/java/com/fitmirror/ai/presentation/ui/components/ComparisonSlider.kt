package com.fitmirror.ai.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.utils.glassmorphic
import com.fitmirror.ai.utils.pressScale

@Composable
fun ComparisonSlider(
    @DrawableRes beforeImageRes: Int,
    @DrawableRes afterImageRes: Int,
    isShowingFront: Boolean,
    onToggleFrontBack: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var splitFraction by remember { mutableFloatStateOf(0.5f) }
    var isSplitMode by remember { mutableFloatStateOf(0.5f) } // default 50-50 split comparison

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp)
            .clip(GlassCardShape)
            .border(
                width = 1.2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                shape = GlassCardShape
            )
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val delta = dragAmount.x / size.width
                    splitFraction = (splitFraction + delta).coerceIn(0.05f, 0.95f)
                }
            }
    ) {
        // Base layer: AFTER image (AI Try-on result)
        AsyncImage(
            model = afterImageRes,
            contentDescription = "After",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Clipped top layer: BEFORE image (User Photo)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(splitFraction)
                .clip(GlassCardShape)
        ) {
            AsyncImage(
                model = beforeImageRes,
                contentDescription = "Before",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1f / splitFraction) // scale to counteract container width
            )
        }

        // Vertical Divider line with luxury drag handle
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(3.dp)
                .align(Alignment.CenterStart)
                .padding(start = 0.dp)
                .background(ElectricBlue)
                .align { size, space, _ ->
                    androidx.compose.ui.unit.IntOffset(
                        x = (space.width * splitFraction).toInt() - (size.width / 2),
                        y = 0
                    )
                }
        )

        // Draggable Circle Handle
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.75f))
                .border(2.dp, ElectricBlue, CircleShape)
                .align { size, space, _ ->
                    androidx.compose.ui.unit.IntOffset(
                        x = (space.width * splitFraction).toInt() - (size.width / 2),
                        y = (space.height / 2) - (size.height / 2)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CompareArrows,
                contentDescription = "Drag to compare",
                tint = PureWhite,
                modifier = Modifier.size(22.dp)
            )
        }

        // Top Badges: "Before" and "After"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(PillShape)
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Before",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                )
            }

            Box(
                modifier = Modifier
                    .clip(PillShape)
                    .background(DeepPurple.copy(alpha = 0.85f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "After (AI)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                )
            }
        }

        // Bottom Toggle: Front vs Back View
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .clip(PillShape)
                .background(Color.Black.copy(alpha = 0.7f))
                .glassmorphic(shape = PillShape, borderWidth = 1.dp)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ViewTogglePill(
                text = "Front View",
                isSelected = isShowingFront,
                onClick = { onToggleFrontBack(true) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            ViewTogglePill(
                text = "Back View",
                isSelected = !isShowingFront,
                onClick = { onToggleFrontBack(false) }
            )
        }
    }
}

@Composable
private fun ViewTogglePill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(PillShape)
            .background(if (isSelected) ElectricBlue else Color.Transparent)
            .pressScale(minScale = 0.95f, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else PureWhite.copy(alpha = 0.8f)
            )
        )
    }
}
