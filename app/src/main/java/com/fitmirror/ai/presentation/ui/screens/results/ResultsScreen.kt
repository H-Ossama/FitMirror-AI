package com.fitmirror.ai.presentation.ui.screens.results

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.R
import com.fitmirror.ai.presentation.ui.components.ComparisonSlider
import com.fitmirror.ai.presentation.ui.components.FitMirrorHeroButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorPrimaryButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorSecondaryButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorTopAppBar
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.viewmodels.ResultsViewModel
import com.fitmirror.ai.utils.glassmorphic
import com.fitmirror.ai.utils.luxuryHeroGradient
import kotlinx.coroutines.delay

@Composable
fun ResultsScreen(
    resultIdParam: String,
    viewModel: ResultsViewModel,
    onBackClick: () -> Unit,
    onGenerateAgain: () -> Unit,
    onTryAnotherOutfit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(resultIdParam) {
        viewModel.loadResult(resultIdParam)
    }

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2800)
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            FitMirrorTopAppBar(
                title = "Try-On Result",
                subtitle = uiState.result?.brand ?: "Luxury Studio",
                onBackClick = onBackClick,
                actions = {
                    val res = uiState.result
                    if (res != null) {
                        IconButton(
                            onClick = { viewModel.toggleFavorite() },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.45f))
                        ) {
                            Icon(
                                imageVector = if (res.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (res.isFavorite) Color(0xFFFF3B30) else PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(luxuryHeroGradient())
                .padding(innerPadding)
        ) {
            val result = uiState.result
            if (result != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                        .padding(bottom = 36.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Large Interactive Before/After & Front/Back Slider
                    val currentAfterRes = if (uiState.isShowingFront) result.frontResultRes else result.backResultRes
                    ComparisonSlider(
                        beforeImageRes = result.userImageRes,
                        afterImageRes = currentAfterRes,
                        isShowingFront = uiState.isShowingFront,
                        onToggleFrontBack = { isFront -> viewModel.toggleFrontBack(isFront) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Outfit Details & Fit Score Card
                    LuxuryCard(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = 20.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = result.brand,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = result.title,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(PillShape)
                                        .background(ElectricBlue.copy(alpha = 0.2f))
                                        .padding(horizontal = 14.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "${result.fitScore}% Match",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = ElectricBlue
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = result.notes,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Quick Actions Row: Save, Download, Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FitMirrorSecondaryButton(
                            text = "Save",
                            onClick = {
                                viewModel.toggleFavorite()
                                viewModel.saveToWardrobe()
                            },
                            icon = Icons.Default.Bookmark,
                            modifier = Modifier.weight(1f)
                        )

                        FitMirrorSecondaryButton(
                            text = "Download",
                            onClick = { viewModel.downloadImage() },
                            icon = Icons.Default.Download,
                            modifier = Modifier.weight(1f)
                        )

                        FitMirrorSecondaryButton(
                            text = "Share",
                            onClick = { viewModel.shareImage() },
                            icon = Icons.Default.Share,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Main Action Buttons
                    FitMirrorHeroButton(
                        text = "✨ Generate Again",
                        onClick = onGenerateAgain,
                        icon = Icons.Default.Refresh
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    FitMirrorSecondaryButton(
                        text = "Try Another Outfit",
                        onClick = onTryAnotherOutfit,
                        icon = Icons.Default.AutoAwesome
                    )
                }
            }

            // Glass Toast / Snackbar overlay
            AnimatedVisibility(
                visible = uiState.toastMessage != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 24.dp)
            ) {
                val msg = uiState.toastMessage
                if (msg != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(PillShape)
                            .background(Color.Black.copy(alpha = 0.88f))
                            .glassmorphic(shape = PillShape, borderWidth = 1.dp)
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = PureWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}
