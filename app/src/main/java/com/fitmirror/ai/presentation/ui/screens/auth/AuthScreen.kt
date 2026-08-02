package com.fitmirror.ai.presentation.ui.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitmirror.ai.R
import com.fitmirror.ai.presentation.ui.components.FitMirrorPrimaryButton
import com.fitmirror.ai.presentation.ui.components.FitMirrorSecondaryButton
import com.fitmirror.ai.presentation.ui.components.InfoModal
import com.fitmirror.ai.presentation.ui.components.LuxuryCard
import com.fitmirror.ai.presentation.ui.theme.ElectricBlue
import com.fitmirror.ai.presentation.ui.theme.ErrorRed
import com.fitmirror.ai.presentation.ui.theme.LuxuryDimensions
import com.fitmirror.ai.presentation.ui.theme.PillShape
import com.fitmirror.ai.presentation.viewmodels.AuthViewModel
import com.fitmirror.ai.utils.luxuryHeroGradient

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var emailInput by remember { mutableStateOf("") }
    var activeModal by remember { mutableStateOf<String?>(null) } // "privacy" or "terms"

    if (activeModal == "privacy") {
        InfoModal(
            title = "Privacy Policy",
            content = "FitMirror AI values your privacy. All uploaded full body photographs and clothing images are processed within our secure luxury AI studio pipeline for the sole purpose of generating your virtual try-on preview.\n\nYour images are never sold or shared with third parties. You may delete any image or your entire account history at any time from the Settings screen.",
            onDismiss = { activeModal = null }
        )
    } else if (activeModal == "terms") {
        InfoModal(
            title = "Terms of Service",
            content = "By accessing FitMirror AI, you agree to our Terms of Service. FitMirror AI provides luxury virtual try-on previews for personal exploration and styling inspiration.\n\nMock Data Notice: This version operates as a standalone frontend showcase with high-fidelity luxury mock data and no server dependency.",
            onDismiss = { activeModal = null }
        )
    }

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
                .padding(horizontal = LuxuryDimensions.screenHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Monogram Logo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_fitmirror),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome to FitMirror AI",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "See Yourself Before You Buy.",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            LuxuryCard(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = 24.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.errorMessage != null) {
                        Text(
                            text = uiState.errorMessage!!,
                            style = MaterialTheme.typography.labelMedium.copy(color = ErrorRed),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    // Google Sign In
                    FitMirrorPrimaryButton(
                        text = "Continue with Google",
                        onClick = { viewModel.loginWithGoogle(onNavigateHome) },
                        enabled = !uiState.isLoading,
                        icon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Apple Sign In
                    FitMirrorSecondaryButton(
                        text = "Continue with Apple",
                        onClick = { viewModel.loginWithApple(onNavigateHome) },
                        icon = Icons.Default.PhoneIphone
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Input Row
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = {
                            emailInput = it
                            viewModel.clearError()
                        },
                        label = { Text("Email address") },
                        placeholder = { Text("name@luxury.com") },
                        singleLine = true,
                        shape = PillShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.loginWithEmail(emailInput, onNavigateHome)
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    FitMirrorSecondaryButton(
                        text = "Sign In with Email",
                        onClick = {
                            viewModel.loginWithEmail(emailInput, onNavigateHome)
                        },
                        icon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Guest Mode
                    FitMirrorSecondaryButton(
                        text = "Continue as Guest",
                        onClick = { viewModel.loginAsGuest(onNavigateHome) }
                    )

                    if (uiState.isLoading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            color = ElectricBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Privacy Policy & Terms
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.clickable { activeModal = "privacy" }
                )
                Text(
                    text = "  •  ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                )
                Text(
                    text = "Terms of Service",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.clickable { activeModal = "terms" }
                )
            }
        }
    }
}
