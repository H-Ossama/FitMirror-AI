package com.fitmirror.ai.domain.model

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val points: List<String> = emptyList(),
    val buttonText: String,
    val illustrationTag: String
)
