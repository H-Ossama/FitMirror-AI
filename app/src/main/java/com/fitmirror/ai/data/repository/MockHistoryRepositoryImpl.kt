package com.fitmirror.ai.data.repository

import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.repository.HistoryRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockHistoryRepositoryImpl @Inject constructor(
    private val tryOnRepository: TryOnRepository
) : HistoryRepository {

    // Share state with TryOnRepository for seamless reactivity across screens
    override val history: StateFlow<List<TryOnResult>>
        get() = tryOnRepository.recentTryOns

    override suspend fun getHistory(): List<TryOnResult> {
        return history.value
    }

    override suspend fun toggleFavorite(resultId: String) {
        val currentList = history.value.map { item ->
            if (item.id == resultId) {
                item.copy(isFavorite = !item.isFavorite)
            } else {
                item
            }
        }
        updateHistory(currentList)
    }

    override suspend fun deleteTryOn(resultId: String) {
        val updated = history.value.filterNot { it.id == resultId }
        updateHistory(updated)
    }

    override suspend fun searchHistory(query: String, filterFavoriteOnly: Boolean): List<TryOnResult> {
        return history.value.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.brand.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)
            val matchesFavorite = !filterFavoriteOnly || item.isFavorite
            matchesQuery && matchesFavorite
        }
    }

    override suspend fun getResultById(resultId: String): TryOnResult? {
        return history.value.find { it.id == resultId }
    }

    private suspend fun updateHistory(newList: List<TryOnResult>) {
        tryOnRepository.replaceHistory(newList)
    }
}
