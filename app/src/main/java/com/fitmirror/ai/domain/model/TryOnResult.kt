package com.fitmirror.ai.domain.model

import androidx.annotation.DrawableRes

data class TryOnResult(
    val id: String,
    val title: String,
    val brand: String,
    val category: String,
    @DrawableRes val userImageRes: Int,
    @DrawableRes val clothingImageRes: Int,
    @DrawableRes val frontResultRes: Int,
    @DrawableRes val backResultRes: Int,
    val fitScore: Int = 98,
    val date: String = "Today, 18:45",
    val isFavorite: Boolean = false,
    val quality: QualityOption = QualityOption.HD,
    val notes: String = "Silk drape and tailored shoulder silhouette matched."
)
