package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.domain.model.SubscriptionPlan
import com.fitmirror.ai.domain.repository.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubscriptionUiState(
    val plans: List<SubscriptionPlan> = emptyList(),
    val currentPlan: SubscriptionPlan? = null,
    val isAnnualBilling: Boolean = true,
    val toastMessage: String? = null
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _toastMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SubscriptionUiState> = combine(
        subscriptionRepository.plans,
        subscriptionRepository.currentPlan,
        subscriptionRepository.isAnnualBilling,
        _toastMessage
    ) { plansList, current, isAnnual, toast ->
        SubscriptionUiState(
            plans = plansList,
            currentPlan = current,
            isAnnualBilling = isAnnual,
            toastMessage = toast
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SubscriptionUiState()
    )

    fun toggleBillingCycle(isAnnual: Boolean) {
        viewModelScope.launch {
            subscriptionRepository.toggleBillingCycle(isAnnual)
        }
    }

    fun selectPlan(planId: String) {
        viewModelScope.launch {
            val result = subscriptionRepository.selectPlan(planId)
            result.onSuccess { newPlan ->
                _toastMessage.value = "✨ Welcome to FitMirror AI ${newPlan.name} Membership"
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
