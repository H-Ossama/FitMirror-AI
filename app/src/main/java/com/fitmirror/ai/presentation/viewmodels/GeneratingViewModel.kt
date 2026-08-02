package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.R
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.repository.TryOnRepository
import com.fitmirror.ai.domain.usecase.GenerateTryOnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GeneratingUiState(
    val progress: Float = 0f,
    val isCompleted: Boolean = false,
    val generatedResultId: String? = null
)

@HiltViewModel
class GeneratingViewModel @Inject constructor(
    private val tryOnRepository: TryOnRepository,
    private val generateTryOnUseCase: GenerateTryOnUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeneratingUiState())
    val uiState: StateFlow<GeneratingUiState> = _uiState.asStateFlow()

    fun startGenerating(clothingId: String, qualityName: String) {
        viewModelScope.launch {
            _uiState.value = GeneratingUiState(progress = 0.0f, isCompleted = false)

            val clothingItem = tryOnRepository.getClothingById(clothingId)
                ?: tryOnRepository.clothingCatalog.value.first()

            val qualityOption = QualityOption.values().find {
                it.name.equals(qualityName, ignoreCase = true) || it.label.equals(qualityName, ignoreCase = true)
            } ?: QualityOption.HD

            // Step 1: Finding body (0% to 25%)
            for (step in 1..25) {
                delay(20)
                _uiState.value = _uiState.value.copy(progress = step / 100f)
            }

            // Step 2: Analyzing clothes (26% to 55%)
            for (step in 26..55) {
                delay(25)
                _uiState.value = _uiState.value.copy(progress = step / 100f)
            }

            // Step 3: Matching pose (56% to 85%)
            for (step in 56..85) {
                delay(25)
                _uiState.value = _uiState.value.copy(progress = step / 100f)
            }

            // Perform actual repository call
            val result = generateTryOnUseCase(
                userPhotoRes = R.drawable.sample_model_female,
                clothingItem = clothingItem,
                quality = qualityOption
            )

            // Step 4: Finalizing (86% to 100%)
            for (step in 86..100) {
                delay(15)
                _uiState.value = _uiState.value.copy(progress = step / 100f)
            }

            val resultId = result.getOrNull()?.id ?: tryOnRepository.getLatestResult()?.id ?: "result_001"
            _uiState.value = _uiState.value.copy(
                progress = 1.0f,
                isCompleted = true,
                generatedResultId = resultId
            )
        }
    }
}
