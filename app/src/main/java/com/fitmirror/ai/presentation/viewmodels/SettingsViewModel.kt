package com.fitmirror.ai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmirror.ai.domain.model.LanguageOption
import com.fitmirror.ai.domain.model.ThemeMode
import com.fitmirror.ai.domain.model.User
import com.fitmirror.ai.domain.repository.AuthRepository
import com.fitmirror.ai.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val user: User? = null,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: LanguageOption = LanguageOption.ENGLISH,
    val notificationsEnabled: Boolean = true,
    val privacyModeEnabled: Boolean = true,
    val isDeleteModalOpen: Boolean = false,
    val activeInfoModal: String? = null // "privacy", "terms", "about", or null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _isDeleteModalOpen = MutableStateFlow(false)
    private val _activeInfoModal = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SettingsUiState> = combine(
        authRepository.currentUser,
        settingsRepository.themeMode,
        settingsRepository.language,
        settingsRepository.notificationsEnabled,
        settingsRepository.privacyModeEnabled
    ) { user, theme, lang, notifs, priv ->
        SettingsUiState(
            user = user,
            themeMode = theme,
            language = lang,
            notificationsEnabled = notifs,
            privacyModeEnabled = priv,
            isDeleteModalOpen = _isDeleteModalOpen.value,
            activeInfoModal = _activeInfoModal.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun setLanguage(lang: LanguageOption) {
        viewModelScope.launch {
            settingsRepository.setLanguage(lang)
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            settingsRepository.toggleNotifications()
        }
    }

    fun togglePrivacyMode() {
        viewModelScope.launch {
            settingsRepository.togglePrivacyMode()
        }
    }

    fun openDeleteModal() {
        _isDeleteModalOpen.value = true
    }

    fun closeDeleteModal() {
        _isDeleteModalOpen.value = false
    }

    fun confirmDeleteAccount(onSuccess: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.deleteAccountData()
            _isDeleteModalOpen.value = false
            onSuccess()
        }
    }

    fun openInfoModal(type: String) {
        _activeInfoModal.value = type
    }

    fun closeInfoModal() {
        _activeInfoModal.value = null
    }
}
