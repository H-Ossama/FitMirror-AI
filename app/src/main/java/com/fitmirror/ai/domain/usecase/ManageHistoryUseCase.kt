package com.fitmirror.ai.domain.usecase

import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ManageHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    val historyFlow: StateFlow<List<TryOnResult>> = historyRepository.history

    suspend fun toggleFavorite(resultId: String) {
        historyRepository.toggleFavorite(resultId)
    }

    suspend fun deleteTryOn(resultId: String) {
        historyRepository.deleteTryOn(resultId)
    }

    suspend fun searchHistory(query: String, favoriteOnly: Boolean): List<TryOnResult> {
        return historyRepository.searchHistory(query, favoriteOnly)
    }
}
