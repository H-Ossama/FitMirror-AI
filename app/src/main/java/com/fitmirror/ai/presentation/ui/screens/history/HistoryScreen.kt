package com.fitmirror.ai.presentation.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.presentation.ui.components.BottomNavItem
import com.fitmirror.ai.presentation.ui.components.ConfirmDialog
import com.fitmirror.ai.presentation.ui.components.FitMirrorBottomNav
import com.fitmirror.ai.presentation.ui.components.FitMirrorTopAppBar
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.RecentTryOnCard
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ErrorRed
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.viewmodels.HistoryViewModel
import com.fitmirror.ai.utils.getResponsiveGridColumns
import com.fitmirror.ai.utils.luxuryHeroGradient

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateToResult: (String) -> Unit,
    onNavigateBottomNav: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.itemToDelete != null) {
        val target = uiState.itemToDelete!!
        ConfirmDialog(
            title = "Delete Outfit?",
            message = "Are you sure you want to remove '${target.title}' from your FitMirror history? This action cannot be undone.",
            confirmText = "Delete",
            dismissText = "Cancel",
            isDestructive = true,
            onConfirm = { viewModel.confirmDelete() },
            onDismiss = { viewModel.dismissDeleteDialog() }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            FitMirrorTopAppBar(
                title = "Try-On History",
                subtitle = "${uiState.filteredHistory.size} saved looks"
            )
        },
        bottomBar = {
            FitMirrorBottomNav(
                currentRoute = BottomNavItem.HISTORY.route,
                onNavigate = onNavigateBottomNav
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Search Bar
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    label = { Text("Search outfits, brands, styles...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = PillShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DeepPurple,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.22f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Filter Pills Row (All vs Favorites)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FilterPill(
                        text = "All Looks",
                        isSelected = !uiState.filterFavoriteOnly,
                        onClick = {
                            if (uiState.filterFavoriteOnly) viewModel.toggleFavoriteFilter()
                        }
                    )
                    FilterPill(
                        text = "Favorites Only ♥",
                        isSelected = uiState.filterFavoriteOnly,
                        onClick = {
                            if (!uiState.filterFavoriteOnly) viewModel.toggleFavoriteFilter()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grid Layout of Previous Results
                if (uiState.filteredHistory.isNotEmpty()) {
                    val columns = getResponsiveGridColumns(baseColumns = 2)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(uiState.filteredHistory, key = { it.id }) { result ->
                            Box {
                                RecentTryOnCard(
                                    result = result,
                                    onClick = { onNavigateToResult(result.id) },
                                    onFavoriteClick = { viewModel.toggleFavorite(result.id) },
                                    isGridView = true
                                )

                                // Delete Button badge in top-left corner
                                IconButton(
                                    onClick = { viewModel.promptDelete(result) },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(10.dp)
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(ErrorRed.copy(alpha = 0.85f))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = PureWhite,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        LuxuryCard(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            contentPadding = 24.dp
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "No try-ons match your search",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Try clearing your search query or creating a new look.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterPill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(PillShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
