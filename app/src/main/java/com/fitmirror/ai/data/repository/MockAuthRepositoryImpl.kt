package com.fitmirror.ai.data.repository

import com.fitmirror.ai.data.mock.MockData
import com.fitmirror.ai.domain.model.MembershipTier
import com.fitmirror.ai.domain.model.User
import com.fitmirror.ai.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(MockData.sampleUser)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(true)
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    override suspend fun loginWithGoogle(): Result<User> {
        delay(600)
        val user = MockData.sampleUser.copy(name = "Elena Rostova (Google)")
        _currentUser.value = user
        _isAuthenticated.value = true
        return Result.success(user)
    }

    override suspend fun loginWithApple(): Result<User> {
        delay(600)
        val user = MockData.sampleUser.copy(name = "Elena Rostova (Apple)")
        _currentUser.value = user
        _isAuthenticated.value = true
        return Result.success(user)
    }

    override suspend fun loginWithEmail(email: String): Result<User> {
        delay(600)
        val displayName = email.substringBefore("@").replace(".", " ").replaceFirstChar { it.uppercase() }
        val user = MockData.sampleUser.copy(email = email, name = displayName)
        _currentUser.value = user
        _isAuthenticated.value = true
        return Result.success(user)
    }

    override suspend fun loginAsGuest(): Result<User> {
        delay(400)
        val guestUser = User(
            id = "guest_user_999",
            name = "Guest Visitor",
            email = "guest@fitmirror.ai",
            membershipTier = MembershipTier.FREE,
            remainingCredits = 5,
            maxCredits = 5,
            isGuest = true,
            totalTryOns = 0
        )
        _currentUser.value = guestUser
        _isAuthenticated.value = true
        return Result.success(guestUser)
    }

    override suspend fun logout() {
        delay(300)
        _currentUser.value = null
        _isAuthenticated.value = false
    }
}
