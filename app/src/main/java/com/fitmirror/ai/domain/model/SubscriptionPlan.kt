package com.fitmirror.ai.domain.model

data class SubscriptionPlan(
    val id: String,
    val name: String,
    val tagline: String,
    val monthlyPrice: String,
    val annualPrice: String,
    val features: List<String>,
    val isPopular: Boolean = false,
    val isCurrentPlan: Boolean = false,
    val tier: MembershipTier
)
