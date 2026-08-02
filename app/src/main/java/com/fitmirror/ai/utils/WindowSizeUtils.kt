package com.fitmirror.ai.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class DeviceFormFactor {
    PHONE,
    TABLET,
    FOLDABLE
}

@Composable
fun rememberDeviceFormFactor(): DeviceFormFactor {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    return when {
        screenWidthDp >= 840 -> DeviceFormFactor.TABLET
        screenWidthDp >= 600 -> DeviceFormFactor.FOLDABLE
        else -> DeviceFormFactor.PHONE
    }
}

@Composable
fun getResponsiveGridColumns(baseColumns: Int = 2): Int {
    return when (rememberDeviceFormFactor()) {
        DeviceFormFactor.TABLET -> baseColumns + 2
        DeviceFormFactor.FOLDABLE -> baseColumns + 1
        DeviceFormFactor.PHONE -> baseColumns
    }
}

@Composable
fun getResponsiveContentPadding(): Dp {
    return when (rememberDeviceFormFactor()) {
        DeviceFormFactor.TABLET -> 32.dp
        DeviceFormFactor.FOLDABLE -> 24.dp
        DeviceFormFactor.PHONE -> 16.dp
    }
}
