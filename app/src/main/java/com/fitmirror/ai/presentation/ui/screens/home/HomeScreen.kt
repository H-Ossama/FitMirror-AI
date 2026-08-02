package com.fitmirror.ai.presentation.ui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.presentation.ui.components.BottomNavItem
import com.fitmirror.ai.presentation.ui.components.CatalogClothingCard
import com.fitmirror.ai.presentation.ui.components.CreditsBadge
import com.fitmirror.ai.presentation.ui.components.FitMirrorBottomNav
import com.fitmirror.ai.presentation.ui.components.FitMirrorHeroButton
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.RecentTryOnCard
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.viewmodels.HomeViewModel
import com.fitmirror.ai.utils.luxuryHeroGradient
import com.fitmirror.ai.utils.pressScale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToUpload: (String) -> Unit,
    onNavigateToResult: (String) -> Unit,
    onNavigateToSubscription: () -> Unit,
    onNavigateBottomNav: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val homeData by viewModel.homeData.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            FitMirrorBottomNav(
                currentRoute = BottomNavItem.HOME.route,
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
            val data = homeData
            if (data != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 28.dp)
                ) {
                    // Top Header Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = LuxuryDimensions.screenHorizontalPadding,
                                vertical = 16.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Profile & Greeting
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (data.user != null) {
                                AsyncImage(
                                    model = data.user.avatarRes,
                                    contentDescription = data.user.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            Column {
                                Text(
                                    text = data.greeting,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    // Credits & Subscription Status Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CreditsBadge(
                            remainingCredits = data.remainingCredits,
                            onClick = onNavigateToSubscription
                        )

                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                                .pressScale { onNavigateToSubscription() }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = ElectricBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.currentSubscription.tier.title,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }

                    // Large Hero Button: ✨ New Try-On
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                    ) {
                        FitMirrorHeroButton(
                            text = "✨ New Try-On",
                            onClick = { onNavigateToUpload("none") },
                            icon = Icons.Default.AutoAwesome
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Recent Try-Ons Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Try-ons",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${data.recentTryOns.size} looks",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (data.recentTryOns.isNotEmpty()) {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = LuxuryDimensions.screenHorizontalPadding),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(data.recentTryOns, key = { it.id }) { result ->
                                RecentTryOnCard(
                                    result = result,
                                    onClick = { onNavigateToResult(result.id) }
                                )
                            }
                        }
                    } else {
                        LuxuryCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
                            onClick = { onNavigateToUpload("none") }
                        ) {
                            Text(
                                text = "No try-ons yet. Tap ✨ New Try-On to create your first look!",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Curated Luxury Catalog Section
                    Text(
                        text = "Curated Inspiration",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = LuxuryDimensions.screenHorizontalPadding),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(data.curatedCatalog, key = { it.id }) { item ->
                            CatalogClothingCard(
                                item = item,
                                onClick = { onNavigateToUpload(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
