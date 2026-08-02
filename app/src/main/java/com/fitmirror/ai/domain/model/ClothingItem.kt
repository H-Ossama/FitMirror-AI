package com.fitmirror.ai.domain.model

import androidx.annotation.DrawableRes

data class ClothingItem(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    @DrawableRes val imageRes: Int,
    val season: String,
    val description: String,
    val price: String
)
