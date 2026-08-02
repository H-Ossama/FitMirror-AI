package com.fitmirror.ai.domain.model

enum class QualityOption(val label: String, val description: String, val badge: String) {
    STANDARD("Standard", "Fast generation, 1080p resolution", "1X"),
    HD("HD", "High definition editorial detail, 2K", "2X"),
    ULTRA_4K("4K", "Ultra-HD luxury studio render", "4X")
}
