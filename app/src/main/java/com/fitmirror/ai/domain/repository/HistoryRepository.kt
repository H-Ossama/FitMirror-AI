package com.fitmirror.ai.domain.repository

import com.fitmirror.ai.domain.model.TryOnResult
import kotlinx.coroutines.flow.StateFlow

interface HistoryRepository {
    val history: StateFlow<List<TryOnResult>>

    suspend fun getHistory(): List<TryOnResult>
    suspend fun toggleFavorite(resultId: String)
    suspend fun deleteTryOn(resultId: String)
    suspend fun searchHistory(query: String, filterFavoriteOnly: Boolean): List<TryOnResult>
    suspend fun getResultById(resultId: String): TryOnResult?
}
