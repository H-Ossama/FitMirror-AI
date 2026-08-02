package com.fitmirror.ai.domain.repository

import com.fitmirror.ai.domain.model.SubscriptionPlan
import kotlinx.coroutines.flow.StateFlow

interface SubscriptionRepository {
    val plans: StateFlow<List<SubscriptionPlan>>
    val currentPlan: StateFlow<SubscriptionPlan>
    val isAnnualBilling: StateFlow<Boolean>

    suspend fun toggleBillingCycle(annual: Boolean)
    suspend fun selectPlan(planId: String): Result<SubscriptionPlan>
}
