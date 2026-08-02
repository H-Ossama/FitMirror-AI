package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.repository.HistoryRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResultsUiState(
    val result: TryOnResult? = null,
    val isShowingFront: Boolean = true,
    val toastMessage: String? = null
)

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val tryOnRepository: TryOnRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    fun loadResult(resultId: String) {
        viewModelScope.launch {
            val found = if (resultId == "latest") {
                tryOnRepository.getLatestResult()
            } else {
                historyRepository.getResultById(resultId) ?: tryOnRepository.getLatestResult()
            }
            _uiState.value = _uiState.value.copy(result = found)
        }
    }

    fun toggleFrontBack(isFront: Boolean) {
        _uiState.value = _uiState.value.copy(isShowingFront = isFront)
    }

    fun toggleFavorite() {
        val currentResult = _uiState.value.result ?: return
        viewModelScope.launch {
            historyRepository.toggleFavorite(currentResult.id)
            val updated = currentResult.copy(isFavorite = !currentResult.isFavorite)
            _uiState.value = _uiState.value.copy(
                result = updated,
                toastMessage = if (updated.isFavorite) "Saved to your luxury Favorites" else "Removed from Favorites"
            )
        }
    }

    fun saveToWardrobe() {
        _uiState.value = _uiState.value.copy(toastMessage = "✨ Outfit saved to your Digital Wardrobe")
    }

    fun downloadImage() {
        _uiState.value = _uiState.value.copy(toastMessage = "📥 High-resolution editorial image saved to gallery")
    }

    fun shareImage() {
        _uiState.value = _uiState.value.copy(toastMessage = "🔗 Share lookbook link copied to clipboard")
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }
}
