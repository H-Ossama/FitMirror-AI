package com.fitmirror.ai.domain.usecase

import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.SubscriptionPlan
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.model.User
import com.fitmirror.ai.domain.repository.AuthRepository
import com.fitmirror.ai.domain.repository.SubscriptionRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class HomeData(
    val user: User?,
    val greeting: String,
    val recentTryOns: List<TryOnResult>,
    val remainingCredits: Int,
    val currentSubscription: SubscriptionPlan,
    val curatedCatalog: List<ClothingItem>
)

class GetHomeDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tryOnRepository: TryOnRepository,
    private val subscriptionRepository: SubscriptionRepository
) {
    operator fun invoke(): Flow<HomeData> {
        return combine(
            authRepository.currentUser,
            tryOnRepository.recentTryOns,
            tryOnRepository.remainingCredits,
            subscriptionRepository.currentPlan,
            tryOnRepository.clothingCatalog
        ) { user, recent, credits, sub, catalog ->
            val greeting = buildGreeting(user?.name)
            HomeData(
                user = user,
                greeting = greeting,
                recentTryOns = recent,
                remainingCredits = credits,
                currentSubscription = sub,
                curatedCatalog = catalog
            )
        }
    }

    private fun buildGreeting(name: String?): String {
        val firstName = name?.substringBefore(" ") ?: "Explorer"
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val timeGreeting = when (hour) {
            in 5..11 -> "Good Morning,"
            in 12..16 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
        return "$timeGreeting $firstName.\nReady to discover your next outfit?"
    }
}
