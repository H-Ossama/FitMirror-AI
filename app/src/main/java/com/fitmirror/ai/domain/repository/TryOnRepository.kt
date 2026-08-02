package com.fitmirror.ai.domain.repository

import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.model.TryOnResult
import kotlinx.coroutines.flow.StateFlow

interface TryOnRepository {
    val recentTryOns: StateFlow<List<TryOnResult>>
    val clothingCatalog: StateFlow<List<ClothingItem>>
    val remainingCredits: StateFlow<Int>
    val isGenerating: StateFlow<Boolean>

    suspend fun getClothingById(id: String): ClothingItem?
    suspend fun generateTryOn(
        userPhotoRes: Int,
        clothingItem: ClothingItem,
        quality: QualityOption
    ): Result<TryOnResult>
    suspend fun getLatestResult(): TryOnResult?
    suspend fun addTryOnResult(result: TryOnResult)
    suspend fun replaceHistory(newList: List<TryOnResult>)
    suspend fun deductCredit()
}
