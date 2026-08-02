package com.fitmirror.ai.data.repository

import com.fitmirror.ai.data.mock.MockData
import com.fitmirror.ai.domain.model.SubscriptionPlan
import com.fitmirror.ai.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockSubscriptionRepositoryImpl @Inject constructor() : SubscriptionRepository {

    private val _plans = MutableStateFlow<List<SubscriptionPlan>>(MockData.subscriptionPlans)
    override val plans: StateFlow<List<SubscriptionPlan>> = _plans.asStateFlow()

    private val _currentPlan = MutableStateFlow<SubscriptionPlan>(
        MockData.subscriptionPlans.find { it.isCurrentPlan } ?: MockData.subscriptionPlans[1]
    )
    override val currentPlan: StateFlow<SubscriptionPlan> = _currentPlan.asStateFlow()

    private val _isAnnualBilling = MutableStateFlow(true)
    override val isAnnualBilling: StateFlow<Boolean> = _isAnnualBilling.asStateFlow()

    override suspend fun toggleBillingCycle(annual: Boolean) {
        _isAnnualBilling.value = annual
    }

    override suspend fun selectPlan(planId: String): Result<SubscriptionPlan> {
        val selected = _plans.value.find { it.id == planId }
            ?: return Result.failure(IllegalArgumentException("Plan not found: $planId"))

        val updatedList = _plans.value.map { plan ->
            plan.copy(isCurrentPlan = plan.id == planId)
        }
        _plans.value = updatedList
        _currentPlan.value = selected.copy(isCurrentPlan = true)
        return Result.success(selected)
    }
}
