package com.fitmirror.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.fitmirror.ai.domain.model.ThemeMode
import com.fitmirror.ai.domain.repository.SettingsRepository
import com.fitmirror.ai.presentation.ui.navigation.FitMirrorNavGraph
import com.fitmirror.ai.presentation.ui.theme.FitMirrorTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentThemeMode by settingsRepository.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

            FitMirrorTheme(themeMode = currentThemeMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FitMirrorNavGraph()
                }
            }
        }
    }
}
