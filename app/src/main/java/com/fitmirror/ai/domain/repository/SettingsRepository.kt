package com.fitmirror.ai.domain.repository

import com.fitmirror.ai.domain.model.LanguageOption
import com.fitmirror.ai.domain.model.ThemeMode
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val themeMode: StateFlow<ThemeMode>
    val language: StateFlow<LanguageOption>
    val notificationsEnabled: StateFlow<Boolean>
    val privacyModeEnabled: StateFlow<Boolean>

    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(lang: LanguageOption)
    suspend fun toggleNotifications()
    suspend fun togglePrivacyMode()
    suspend fun deleteAccountData(): Result<Unit>
}
