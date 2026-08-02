package com.fitmirror.ai.presentation.ui.screens.upload

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.R
import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.presentation.ui.components.CatalogClothingCard
import com.fitmirror.ai.presentation.ui.components.FitMirrorHeroButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorTopAppBar
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.UploadImageCard
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.FitMatchGreen
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.viewmodels.UploadViewModel
import com.fitmirror.ai.utils.glassmorphic
import com.fitmirror.ai.utils.pressScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    clothingIdParam: String,
    viewModel: UploadViewModel,
    onBackClick: () -> Unit,
    onNavigateToGenerating: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var showPersonPickerSheet by remember { mutableStateOf(false) }
    var showClothingPickerSheet by remember { mutableStateOf(false) }

    LaunchedEffect(clothingIdParam) {
        if (clothingIdParam != "none" && clothingIdParam.isNotBlank()) {
            viewModel.loadInitialClothing(clothingIdParam)
        }
    }

    if (showPersonPickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPersonPickerSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = "Select Luxury Avatar Photo",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LuxuryCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp),
                        onClick = {
                            viewModel.selectPersonPhoto(R.drawable.sample_model_female)
                            showPersonPickerSheet = false
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Elena (Female Model)", fontWeight = FontWeight.SemiBold)
                        }
                    }

                    LuxuryCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp),
                        onClick = {
                            viewModel.selectPersonPhoto(R.drawable.sample_model_male)
                            showPersonPickerSheet = false
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Alexander (Male Model)", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }

    if (showClothingPickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showClothingPickerSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = "Select Designer Garment",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.catalog.forEach { item ->
                        CatalogClothingCard(
                            item = item,
                            onClick = {
                                viewModel.selectClothing(item)
                                showClothingPickerSheet = false
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            FitMirrorTopAppBar(
                title = "New Try-On",
                subtitle = "Luxury Studio Setup",
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                .padding(bottom = 36.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Large Upload Cards (Side by Side or Vertical stack on small phones)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card 1: Your Photo
                UploadImageCard(
                    title = "Your Photo",
                    subtitle = "Full body studio pose",
                    selectedImageRes = uiState.selectedPhotoRes,
                    isPerson = true,
                    onClick = { showPersonPickerSheet = true },
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Clothing Image
                UploadImageCard(
                    title = "Clothing Image",
                    subtitle = uiState.selectedClothing?.brand ?: "Designer garment",
                    selectedImageRes = uiState.selectedClothing?.imageRes,
                    isPerson = false,
                    onClick = { showClothingPickerSheet = true },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Requirements Checklist Section
            Text(
                text = "Studio Requirements",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            LuxuryCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 16.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.requirements.forEachIndexed { index, req ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleRequirement(index) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (req.isChecked) FitMatchGreen else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "✔ ${req.label}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (req.isChecked) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (req.isChecked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Image Quality Selector
            Text(
                text = "Image Quality",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(GlassCardShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .glassmorphic(shape = GlassCardShape, borderWidth = 1.dp)
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QualityOption.values().forEach { option ->
                    val isSelected = uiState.selectedQuality == option
                    val bg by animateColorAsState(
                        targetValue = if (isSelected) DeepPurple else Color.Transparent,
                        label = "qualityBg"
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(PillShape)
                            .background(bg)
                            .pressScale(onClick = { viewModel.selectQuality(option) })
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = option.label,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(ElectricBlue)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = option.badge,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Generate Button
            val selectedClothingId = uiState.selectedClothing?.id ?: "cloth_trench_01"
            val qualityName = uiState.selectedQuality.name
            FitMirrorHeroButton(
                text = "✨ Generate Preview",
                onClick = {
                    if (uiState.canGenerate) {
                        onNavigateToGenerating(selectedClothingId, qualityName)
                    }
                },
                icon = Icons.Default.AutoAwesome
            )
        }
    }
}
