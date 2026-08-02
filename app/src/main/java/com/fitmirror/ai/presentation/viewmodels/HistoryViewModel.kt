package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.usecase.ManageHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val filteredHistory: List<TryOnResult> = emptyList(),
    val searchQuery: String = "",
    val filterFavoriteOnly: Boolean = false,
    val isGridView: Boolean = true,
    val itemToDelete: TryOnResult? = null
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val manageHistoryUseCase: ManageHistoryUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _filterFavoriteOnly = MutableStateFlow(false)
    private val _isGridView = MutableStateFlow(true)
    private val _itemToDelete = MutableStateFlow<TryOnResult?>(null)

    val uiState: StateFlow<HistoryUiState> = combine(
        manageHistoryUseCase.historyFlow,
        _searchQuery,
        _filterFavoriteOnly,
        _isGridView,
        _itemToDelete
    ) { historyList, query, favOnly, isGrid, toDelete ->
        val filtered = historyList.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.brand.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)
            val matchesFav = !favOnly || item.isFavorite
            matchesQuery && matchesFav
        }
        HistoryUiState(
            filteredHistory = filtered,
            searchQuery = query,
            filterFavoriteOnly = favOnly,
            isGridView = isGrid,
            itemToDelete = toDelete
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HistoryUiState()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun toggleFavoriteFilter() {
        _filterFavoriteOnly.value = !_filterFavoriteOnly.value
    }

    fun toggleViewMode() {
        _isGridView.value = !_isGridView.value
    }

    fun toggleFavorite(resultId: String) {
        viewModelScope.launch {
            manageHistoryUseCase.toggleFavorite(resultId)
        }
    }

    fun promptDelete(item: TryOnResult) {
        _itemToDelete.value = item
    }

    fun dismissDeleteDialog() {
        _itemToDelete.value = null
    }

    fun confirmDelete() {
        val target = _itemToDelete.value ?: return
        viewModelScope.launch {
            manageHistoryUseCase.deleteTryOn(target.id)
            _itemToDelete.value = null
        }
    }
}
