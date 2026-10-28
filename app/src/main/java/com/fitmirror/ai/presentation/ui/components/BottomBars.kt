package com.fitmirror.ai.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite

enum class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    HOME("home_screen", "Home", Icons.Default.Home),
    HISTORY("history_screen", "History", Icons.Default.History),
    SUBSCRIPTION("subscription_screen", "Studio", Icons.Default.AutoAwesome),
    SETTINGS("settings_screen", "Profile", Icons.Default.Settings)
}

/**
 * Floating minimal pill navigation — warm-white capsule with a hairline
 * border and a soft shadow. Selected tab becomes a solid ink pill.
 */
@Composable
fun FitMirrorBottomNav(
    currentRoute: String,
    onNavigate: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 40.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = PillShape,
                    ambientColor = Color.Black.copy(alpha = 0.10f),
                    spotColor = Color.Black.copy(alpha = 0.14f)
                )
                .clip(PillShape)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem.values().forEach { item ->
                val isSelected = currentRoute == item.route
                BottomNavItemButton(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigate(item) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemButton(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) DeepPurple else Color.Transparent,
        label = "navBgColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        label = "navContentColor"
    )
    val horizontalPadding by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 8.dp,
        animationSpec = spring(),
        label = "navPadding"
    )

    Row(
        modifier = Modifier
            .clip(PillShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = horizontalPadding)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = contentColor,
            modifier = Modifier.size(20.dp)
        )
        if (isSelected) {
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            )
        }
    }
}
