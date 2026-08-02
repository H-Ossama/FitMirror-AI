package com.fitmirror.ai.presentation.ui.screens.subscription

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.domain.model.SubscriptionPlan
import com.fitmirror.ai.presentation.ui.components.BottomNavItem
import com.fitmirror.ai.presentation.ui.components.FitMirrorBottomNav
import com.fitmirror.ai.presentation.ui.components.FitMirrorPrimaryButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorSecondaryButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorTopAppBar
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.theme.DeepPurple
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.GlassCardShape
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.LuxuryGold
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.ui.theme.PureWhite
import com.fitmirror.ai.presentation.viewmodels.SubscriptionViewModel
import com.fitmirror.ai.utils.glassmorphic
import com.fitmirror.ai.utils.luxuryHeroGradient
import com.fitmirror.ai.utils.pressScale
import kotlinx.coroutines.delay

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel,
    onNavigateBottomNav: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(3000)
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            FitMirrorTopAppBar(
                title = "Subscription Studio",
                subtitle = "Select your luxury tier"
            )
        },
        bottomBar = {
            FitMirrorBottomNav(
                currentRoute = BottomNavItem.SUBSCRIPTION.route,
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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = LuxuryDimensions.screenHorizontalPadding)
                    .padding(bottom = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Billing Cycle Switch (Monthly vs Annual with Save 20%)
                Row(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .glassmorphic(shape = PillShape, borderWidth = 1.dp)
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BillingTogglePill(
                        text = "Monthly",
                        isSelected = !uiState.isAnnualBilling,
                        onClick = { viewModel.toggleBillingCycle(false) }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    BillingTogglePill(
                        text = "Annual (Save 20%)",
                        isSelected = uiState.isAnnualBilling,
                        onClick = { viewModel.toggleBillingCycle(true) },
                        badgeColor = LuxuryGold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Pricing Cards: Free, Pro, Plus
                uiState.plans.forEach { plan ->
                    PricingCard(
                        plan = plan,
                        isAnnual = uiState.isAnnualBilling,
                        onSelectPlan = { viewModel.selectPlan(plan.id) }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // Glass Toast notification
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
                            .background(Color.Black.copy(alpha = 0.9f))
                            .glassmorphic(shape = PillShape, borderWidth = 1.dp)
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = PureWhite,
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BillingTogglePill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    badgeColor: Color? = null
) {
    Box(
        modifier = Modifier
            .clip(PillShape)
            .background(if (isSelected) DeepPurple else Color.Transparent)
            .pressScale(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
            if (badgeColor != null && isSelected) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(badgeColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "VIP",
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

@Composable
private fun PricingCard(
    plan: SubscriptionPlan,
    isAnnual: Boolean,
    onSelectPlan: () -> Unit
) {
    val isPopular = plan.isPopular
    val borderWidth = if (isPopular) 2.dp else 1.dp

    val cardModifier = if (isPopular) {
        Modifier
            .fillMaxWidth()
            .border(
                width = borderWidth,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        ElectricBlue,
                        LuxuryGold,
                        ElectricBlue
                    )
                ),
                shape = GlassCardShape
            )
    } else {
        Modifier.fillMaxWidth()
    }

    LuxuryCard(
        modifier = cardModifier,
        contentPadding = 24.dp
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = plan.name,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = plan.tagline,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }

                if (isPopular) {
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(ElectricBlue)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "MOST POPULAR",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                val priceText = if (isAnnual && plan.annualPrice != "$0") {
                    // display monthly breakdown of annual price
                    if (plan.id == "plan_pro") "$12.00" else "$24.00"
                } else {
                    plan.monthlyPrice
                }
                Text(
                    text = priceText,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = " / month",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            if (isAnnual && plan.annualPrice != "$0") {
                Text(
                    text = "Billed annually as ${plan.annualPrice}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = LuxuryGold
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Features Checklist
            plan.features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = ElectricBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (plan.isCurrentPlan) {
                FitMirrorSecondaryButton(
                    text = "Current Plan",
                    onClick = {}
                )
            } else {
                FitMirrorPrimaryButton(
                    text = "Select ${plan.name} Plan",
                    onClick = onSelectPlan
                )
            }
        }
    }
}
