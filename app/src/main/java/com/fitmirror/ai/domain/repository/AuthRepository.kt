package com.fitmirror.ai.domain.repository

import com.fitmirror.ai.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>
    val isAuthenticated: StateFlow<Boolean>

    suspend fun loginWithGoogle(): Result<User>
    suspend fun loginWithApple(): Result<User>
    suspend fun loginWithEmail(email: String): Result<User>
    suspend fun loginAsGuest(): Result<User>
    suspend fun logout()
}
