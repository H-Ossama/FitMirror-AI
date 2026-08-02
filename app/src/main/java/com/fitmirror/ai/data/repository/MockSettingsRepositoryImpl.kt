package com.fitmirror.ai.data.repository

import com.fitmirror.ai.domain.model.LanguageOption
import com.fitmirror.ai.domain.model.ThemeMode
import com.fitmirror.ai.domain.repository.AuthRepository
import com.fitmirror.ai.domain.repository.SettingsRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockSettingsRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val tryOnRepository: TryOnRepository
) : SettingsRepository {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    override val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _language = MutableStateFlow(LanguageOption.ENGLISH)
    override val language: StateFlow<LanguageOption> = _language.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(true)
    override val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _privacyModeEnabled = MutableStateFlow(true)
    override val privacyModeEnabled: StateFlow<Boolean> = _privacyModeEnabled.asStateFlow()

    override suspend fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    override suspend fun setLanguage(lang: LanguageOption) {
        _language.value = lang
    }

    override suspend fun toggleNotifications() {
        _notificationsEnabled.value = !_notificationsEnabled.value
    }

    override suspend fun togglePrivacyMode() {
        _privacyModeEnabled.value = !_privacyModeEnabled.value
    }

    override suspend fun deleteAccountData(): Result<Unit> {
        delay(600)
        tryOnRepository.replaceHistory(emptyList())
        authRepository.logout()
        return Result.success(Unit)
    }
}
