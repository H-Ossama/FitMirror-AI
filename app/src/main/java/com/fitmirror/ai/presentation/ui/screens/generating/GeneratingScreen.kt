package com.fitmirror.ai.presentation.ui.screens.generating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.components.AnimatedStatusCycler
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.LuxuryProgressBar
import com.fitmirror.ai.presentation.ui.components.LuxuryPulseLoader
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.viewmodels.GeneratingViewModel
import com.fitmirror.ai.utils.luxuryHeroGradient

@Composable
fun GeneratingScreen(
    clothingIdParam: String,
    qualityParam: String,
    viewModel: GeneratingViewModel,
    onNavigateToResults: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(clothingIdParam, qualityParam) {
        viewModel.startGenerating(clothingIdParam, qualityParam)
    }

    LaunchedEffect(uiState.isCompleted, uiState.generatedResultId) {
        if (uiState.isCompleted && uiState.generatedResultId != null) {
            onNavigateToResults(uiState.generatedResultId!!)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(luxuryHeroGradient()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "FITMIRROR AI STUDIO",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = ElectricBlue
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Creating Your Look",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Beautiful Geometric Pulse Loader
            LuxuryPulseLoader(size = 220.dp)

            Spacer(modifier = Modifier.height(48.dp))

            // Animated Status Cycling
            AnimatedStatusCycler(
                statuses = listOf(
                    "Finding body...",
                    "Analyzing clothes...",
                    "Matching pose...",
                    "Generating..."
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Luxury Progress Bar 0% to 100%
            LuxuryCard(
                modifier = Modifier.fillMaxWidth(0.9f),
                contentPadding = 20.dp
            ) {
                LuxuryProgressBar(
                    progress = uiState.progress,
                    showPercentage = true
                )
            }
        }
    }
}
