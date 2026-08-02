package com.fitmirror.ai.data.repository

import com.fitmirror.ai.R
import com.fitmirror.ai.data.mock.MockData
import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.repository.TryOnRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockTryOnRepositoryImpl @Inject constructor() : TryOnRepository {

    private val _recentTryOns = MutableStateFlow<List<TryOnResult>>(MockData.initialHistory)
    override val recentTryOns: StateFlow<List<TryOnResult>> = _recentTryOns.asStateFlow()

    private val _clothingCatalog = MutableStateFlow<List<ClothingItem>>(MockData.clothingCatalog)
    override val clothingCatalog: StateFlow<List<ClothingItem>> = _clothingCatalog.asStateFlow()

    private val _remainingCredits = MutableStateFlow(12)
    override val remainingCredits: StateFlow<Int> = _remainingCredits.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    override val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    override suspend fun getClothingById(id: String): ClothingItem? {
        return _clothingCatalog.value.find { it.id == id }
    }

    override suspend fun generateTryOn(
        userPhotoRes: Int,
        clothingItem: ClothingItem,
        quality: QualityOption
    ): Result<TryOnResult> {
        _isGenerating.value = true
        delay(2400) // Simulated realistic processing time for AI

        val (frontRes, backRes) = when (clothingItem.id) {
            "cloth_dress_02" -> R.drawable.sample_result_dress_front to R.drawable.sample_result_dress_front
            "cloth_suit_03" -> R.drawable.sample_result_suit_front to R.drawable.sample_result_suit_front
            else -> R.drawable.sample_result_trench_front to R.drawable.sample_result_trench_back
        }

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val newResult = TryOnResult(
            id = "res_${UUID.randomUUID().toString().take(8)}",
            title = clothingItem.title,
            brand = clothingItem.brand,
            category = clothingItem.category,
            userImageRes = userPhotoRes,
            clothingImageRes = clothingItem.imageRes,
            frontResultRes = frontRes,
            backResultRes = backRes,
            fitScore = 98,
            date = "Today, $currentTime",
            isFavorite = false,
            quality = quality,
            notes = "Custom ${quality.label} AI garment wrap simulation complete with editorial lighting."
        )

        _recentTryOns.value = listOf(newResult) + _recentTryOns.value
        if (_remainingCredits.value > 0) {
            _remainingCredits.value = _remainingCredits.value - 1
        }
        _isGenerating.value = false
        return Result.success(newResult)
    }

    override suspend fun getLatestResult(): TryOnResult? {
        return _recentTryOns.value.firstOrNull()
    }

    override suspend fun addTryOnResult(result: TryOnResult) {
        val current = _recentTryOns.value.toMutableList()
        current.removeAll { it.id == result.id }
        current.add(0, result)
        _recentTryOns.value = current
    }

    override suspend fun replaceHistory(newList: List<TryOnResult>) {
        _recentTryOns.value = newList
    }

    override suspend fun deductCredit() {
        if (_remainingCredits.value > 0) {
            _remainingCredits.value = _remainingCredits.value - 1
        }
    }
}
