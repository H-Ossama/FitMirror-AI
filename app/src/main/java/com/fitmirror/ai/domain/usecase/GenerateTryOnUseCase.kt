package com.fitmirror.ai.domain.usecase

import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.repository.HistoryRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import javax.inject.Inject

class GenerateTryOnUseCase @Inject constructor(
    private val tryOnRepository: TryOnRepository,
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(
        userPhotoRes: Int,
        clothingItem: ClothingItem,
        quality: QualityOption
    ): Result<TryOnResult> {
        val result = tryOnRepository.generateTryOn(userPhotoRes, clothingItem, quality)
        return result
    }
}
