package com.fitmirror.ai.presentation.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fitmirror.ai.R
import com.fitmirror.ai.data.mock.MockData
import com.fitmirror.ai.domain.model.OnboardingPage
import com.fitmirror.ai.presentation.ui.components.FitMirrorPrimaryButton
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.components.PageIndicator
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.utils.luxuryHeroGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = MockData.onboardingPages
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(luxuryHeroGradient())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
        ) {
            // Top Bar with Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage < pages.size - 1) {
                    TextButton(onClick = onFinishOnboarding) {
                        Text(
                            text = "SKIP",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                            )
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                OnboardingPageContent(page = pages[pageIndex], index = pageIndex)
            }

            Spacer(modifier = Modifier.height(14.dp))

            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            val isLastPage = pagerState.currentPage == pages.size - 1
            FitMirrorPrimaryButton(
                text = if (isLastPage) "Get Started" else "Continue",
                onClick = {
                    if (isLastPage) {
                        onFinishOnboarding()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                icon = if (isLastPage) Icons.Default.AutoAwesome else Icons.Default.ArrowForward,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    index: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Editorial full-bleed imagery
        if (index == 1) {
            // Step page: clean hairline card with numbered steps
            LuxuryCard(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .aspectRatio(1.15f),
                shape = GlassCardShape,
                contentPadding = 20.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepIconBadge("1. SELF", Icons.Default.Person)
                    Text("—", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f), fontWeight = FontWeight.Normal)
                    StepIconBadge("2. GARMENT", Icons.Default.Checkroom)
                    Text("—", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f), fontWeight = FontWeight.Normal)
                    StepIconBadge("3. AI", Icons.Default.AutoAwesome)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .aspectRatio(1.12f)
                    .clip(GlassCardShape)
            ) {
                AsyncImage(
                    model = if (index == 0) R.drawable.sample_result_trench_front else R.drawable.sample_result_dress_front,
                    contentDescription = page.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(GlassCardShape)
                        .background(Color.Black.copy(alpha = 0.72f))
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = if (index == 0) "AI GARMENT SYNTHESIS" else "ZERO GUESSWORK",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = page.subtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (page.points.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(0.85f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                page.points.forEach { point ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(ElectricBlue.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(ElectricBlue)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepIconBadge(label: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DeepPurple,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}
