package com.fitmirror.ai.domain.model

import androidx.annotation.DrawableRes
import com.fitmirror.ai.R

enum class MembershipTier(val title: String) {
    FREE("Free Plan"),
    PRO("Pro Member"),
    PLUS("Luxury Studio")
}

data class User(
    val id: String = "user_001",
    val name: String = "Elena Rostova",
    val email: String = "elena.rostova@luxury.com",
    @DrawableRes val avatarRes: Int = R.drawable.sample_model_female,
    val membershipTier: MembershipTier = MembershipTier.PRO,
    val remainingCredits: Int = 12,
    val maxCredits: Int = 15,
    val isGuest: Boolean = false,
    val totalTryOns: Int = 24
)
