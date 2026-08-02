package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.domain.model.User
import com.fitmirror.ai.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.currentUser
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val isAuthenticated: StateFlow<Boolean> = authRepository.isAuthenticated
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun loginWithGoogle(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.loginWithGoogle()
            result.onSuccess {
                _uiState.value = AuthUiState(isLoading = false)
                onSuccess()
            }.onFailure { err ->
                _uiState.value = AuthUiState(isLoading = false, errorMessage = err.message)
            }
        }
    }

    fun loginWithApple(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.loginWithApple()
            result.onSuccess {
                _uiState.value = AuthUiState(isLoading = false)
                onSuccess()
            }.onFailure { err ->
                _uiState.value = AuthUiState(isLoading = false, errorMessage = err.message)
            }
        }
    }

    fun loginWithEmail(email: String, onSuccess: () -> Unit) {
        if (email.isBlank() || !email.contains("@")) {
            _uiState.value = AuthUiState(errorMessage = "Please enter a valid email address")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.loginWithEmail(email)
            result.onSuccess {
                _uiState.value = AuthUiState(isLoading = false)
                onSuccess()
            }.onFailure { err ->
                _uiState.value = AuthUiState(isLoading = false, errorMessage = err.message)
            }
        }
    }

    fun loginAsGuest(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.loginAsGuest()
            result.onSuccess {
                _uiState.value = AuthUiState(isLoading = false)
                onSuccess()
            }.onFailure { err ->
                _uiState.value = AuthUiState(isLoading = false, errorMessage = err.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
