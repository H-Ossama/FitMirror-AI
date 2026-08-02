package com.fitmirror.ai.presentation.viewmodels

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.R
import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.repository.TryOnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RequirementItem(
    val label: String,
    val isChecked: Boolean = true
)

data class UploadUiState(
    @DrawableRes val selectedPhotoRes: Int? = R.drawable.sample_model_female,
    val selectedClothing: ClothingItem? = null,
    val catalog: List<ClothingItem> = emptyList(),
    val selectedQuality: QualityOption = QualityOption.HD,
    val requirements: List<RequirementItem> = listOf(
        RequirementItem("Full Body", true),
        RequirementItem("Standing Position", true),
        RequirementItem("Good Lighting", true),
        RequirementItem("No Mirror Selfies", true)
    )
) {
    val canGenerate: Boolean
        get() = selectedPhotoRes != null && selectedClothing != null && requirements.all { it.isChecked }
}

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val tryOnRepository: TryOnRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tryOnRepository.clothingCatalog.collect { items ->
                _uiState.value = _uiState.value.copy(
                    catalog = items,
                    selectedClothing = _uiState.value.selectedClothing ?: items.firstOrNull()
                )
            }
        }
    }

    fun loadInitialClothing(clothingId: String) {
        viewModelScope.launch {
            val found = tryOnRepository.getClothingById(clothingId)
            if (found != null) {
                _uiState.value = _uiState.value.copy(selectedClothing = found)
            }
        }
    }

    fun selectPersonPhoto(@DrawableRes res: Int) {
        _uiState.value = _uiState.value.copy(selectedPhotoRes = res)
    }

    fun selectClothing(item: ClothingItem) {
        _uiState.value = _uiState.value.copy(selectedClothing = item)
    }

    fun selectQuality(quality: QualityOption) {
        _uiState.value = _uiState.value.copy(selectedQuality = quality)
    }

    fun toggleRequirement(index: Int) {
        val currentList = _uiState.value.requirements.toMutableList()
        if (index in currentList.indices) {
            val item = currentList[index]
            currentList[index] = item.copy(isChecked = !item.isChecked)
            _uiState.value = _uiState.value.copy(requirements = currentList)
        }
    }
}
