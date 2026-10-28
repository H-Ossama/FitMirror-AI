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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fitmirror.ai.R
import com.fitmirror.ai.presentation.ui.components.BottomNavItem
import com.fitmirror.ai.presentation.ui.components.CatalogClothingCard
import com.fitmirror.ai.presentation.ui.components.CreditsBadge
import com.fitmirror.ai.presentation.ui.components.FitMirrorBottomNav
import com.fitmirror.ai.presentation.ui.components.FitMirrorHeroButton
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.RecentTryOnCard
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
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
                    // ---- Editorial Header ----
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = LuxuryDimensions.screenHorizontalPadding,
                                vertical = 14.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (data.user != null) {
                                AsyncImage(
                                    model = data.user.avatarRes,
                                    contentDescription = data.user.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                            Column {
                                Text(
                                    text = "FITMIRROR AI",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                                    )
                                )
                                Text(
                                    text = data.greeting,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .pressScale { onNavigateToSubscription() }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = ElectricBlue,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.currentSubscription.tier.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }

                    // ---- Editorial Hero Image ----
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                            .height(300.dp)
                            .clip(GlassCardShape)
                    ) {
                        AsyncImage(
                            model = R.drawable.sample_result_dress_front,
                            contentDescription = "Editorial look",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.75f)
                                        ),
                                        startY = 160f
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "NEW SEASON EDIT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite.copy(alpha = 0.85f)
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "See it on you,\nbefore you buy it.",
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = PureWhite
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // ---- Credits row ----
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CreditsBadge(
                            remainingCredits = data.remainingCredits,
                            onClick = onNavigateToSubscription
                        )
                        Text(
                            text = "${data.recentTryOns.size} saved looks",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // ---- Primary CTA ----
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                    ) {
                        FitMirrorHeroButton(
                            text = "Start a New Try-On",
                            onClick = { onNavigateToUpload("none") },
                            icon = Icons.Default.AutoAwesome
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // ---- Recent Try-Ons ----
                    SectionHeader(title = "Recent Looks", trailing = "${data.recentTryOns.size} looks")
                    Spacer(modifier = Modifier.height(12.dp))

                    if (data.recentTryOns.isNotEmpty()) {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = LuxuryDimensions.screenHorizontalPadding),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
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
                                text = "No try-ons yet. Tap “Start a New Try-On” to create your first look.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // ---- Curated Catalog ----
                    SectionHeader(title = "Curated Inspiration", trailing = "ATELIER PICKS")
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = LuxuryDimensions.screenHorizontalPadding),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
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

@Composable
private fun SectionHeader(title: String, trailing: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        if (trailing != null) {
            Text(
                text = trailing.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                )
            )
        }
    }
}
