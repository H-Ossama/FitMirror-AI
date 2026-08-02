package com.fitmirror.ai.presentation.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fitmirror.ai.presentation.ui.components.BottomNavItem
import com.fitmirror.ai.presentation.ui.screens.auth.AuthScreen
import com.fitmirror.ai.presentation.ui.screens.generating.GeneratingScreen
import com.fitmirror.ai.presentation.ui.screens.history.HistoryScreen
import com.fitmirror.ai.presentation.ui.screens.home.HomeScreen
import com.fitmirror.ai.presentation.ui.screens.onboarding.OnboardingScreen
import com.fitmirror.ai.presentation.ui.screens.results.ResultsScreen
import com.fitmirror.ai.presentation.ui.screens.settings.SettingsScreen
import com.fitmirror.ai.presentation.ui.screens.splash.SplashScreen
import com.fitmirror.ai.presentation.ui.screens.subscription.SubscriptionScreen
import com.fitmirror.ai.presentation.viewmodels.AuthViewModel
import com.fitmirror.ai.presentation.viewmodels.GeneratingViewModel
import com.fitmirror.ai.presentation.viewmodels.HistoryViewModel
import com.fitmirror.ai.presentation.viewmodels.HomeViewModel
import com.fitmirror.ai.presentation.viewmodels.ResultsViewModel
import com.fitmirror.ai.presentation.viewmodels.SettingsViewModel
import com.fitmirror.ai.presentation.viewmodels.SubscriptionViewModel
import com.fitmirror.ai.presentation.viewmodels.UploadViewModel

@Composable
fun FitMirrorNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ScreenRoute.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(380)
            ) + fadeIn(animationSpec = tween(380))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(380)
            ) + fadeOut(animationSpec = tween(380))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(380)
            ) + fadeIn(animationSpec = tween(380))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(380)
            ) + fadeOut(animationSpec = tween(380))
        }
    ) {
        // SCREEN 1: Splash
        composable(ScreenRoute.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(ScreenRoute.Onboarding.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // SCREEN 2: Onboarding
        composable(ScreenRoute.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(ScreenRoute.Auth.route) {
                        popUpTo(ScreenRoute.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // SCREEN 3: Auth
        composable(ScreenRoute.Auth.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                viewModel = authViewModel,
                onNavigateHome = {
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // SCREEN 4: Home (Bottom Nav Tab 1)
        composable(ScreenRoute.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToUpload = { clothingId ->
                    navController.navigate(ScreenRoute.Upload.createRoute(clothingId))
                },
                onNavigateToResult = { resultId ->
                    navController.navigate(ScreenRoute.Results.createRoute(resultId))
                },
                onNavigateToSubscription = {
                    navController.navigate(ScreenRoute.Subscription.route)
                },
                onNavigateBottomNav = { item ->
                    if (item.route != ScreenRoute.Home.route) {
                        navController.navigate(item.route) {
                            popUpTo(ScreenRoute.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

        // SCREEN 5: Upload
        composable(
            route = ScreenRoute.Upload.route,
            arguments = listOf(
                navArgument("clothingId") {
                    type = NavType.StringType
                    defaultValue = "none"
                }
            )
        ) { backStackEntry ->
            val clothingIdParam = backStackEntry.arguments?.getString("clothingId") ?: "none"
            val uploadViewModel: UploadViewModel = hiltViewModel()
            com.fitmirror.ai.presentation.ui.screens.upload.UploadScreen(
                clothingIdParam = clothingIdParam,
                viewModel = uploadViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToGenerating = { clothingId, qualityName ->
                    navController.navigate(ScreenRoute.Generating.createRoute(clothingId, qualityName))
                }
            )
        }

        // SCREEN 6: Generating
        composable(
            route = ScreenRoute.Generating.route,
            arguments = listOf(
                navArgument("clothingId") { type = NavType.StringType; defaultValue = "cloth_trench_01" },
                navArgument("quality") { type = NavType.StringType; defaultValue = "HD" }
            )
        ) { backStackEntry ->
            val clothingId = backStackEntry.arguments?.getString("clothingId") ?: "cloth_trench_01"
            val quality = backStackEntry.arguments?.getString("quality") ?: "HD"
            val generatingViewModel: GeneratingViewModel = hiltViewModel()
            GeneratingScreen(
                clothingIdParam = clothingId,
                qualityParam = quality,
                viewModel = generatingViewModel,
                onNavigateToResults = { resultId ->
                    navController.navigate(ScreenRoute.Results.createRoute(resultId)) {
                        popUpTo(ScreenRoute.Upload.route) { inclusive = true }
                    }
                }
            )
        }

        // SCREEN 7: Results
        composable(
            route = ScreenRoute.Results.route,
            arguments = listOf(
                navArgument("resultId") { type = NavType.StringType; defaultValue = "latest" }
            )
        ) { backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId") ?: "latest"
            val resultsViewModel: ResultsViewModel = hiltViewModel()
            ResultsScreen(
                resultIdParam = resultId,
                viewModel = resultsViewModel,
                onBackClick = { navController.popBackStack() },
                onGenerateAgain = {
                    navController.navigate(ScreenRoute.Upload.createRoute("none"))
                },
                onTryAnotherOutfit = {
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // SCREEN 8: History (Bottom Nav Tab 2)
        composable(ScreenRoute.History.route) {
            val historyViewModel: HistoryViewModel = hiltViewModel()
            HistoryScreen(
                viewModel = historyViewModel,
                onNavigateToResult = { resultId ->
                    navController.navigate(ScreenRoute.Results.createRoute(resultId))
                },
                onNavigateBottomNav = { item ->
                    if (item.route != ScreenRoute.History.route) {
                        navController.navigate(item.route) {
                            popUpTo(ScreenRoute.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

        // SCREEN 9: Subscription (Bottom Nav Tab 3)
        composable(ScreenRoute.Subscription.route) {
            val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
            SubscriptionScreen(
                viewModel = subscriptionViewModel,
                onNavigateBottomNav = { item ->
                    if (item.route != ScreenRoute.Subscription.route) {
                        navController.navigate(item.route) {
                            popUpTo(ScreenRoute.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

        // SCREEN 10: Settings (Bottom Nav Tab 4)
        composable(ScreenRoute.Settings.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateToAuth = {
                    navController.navigate(ScreenRoute.Auth.route) {
                        popUpTo(ScreenRoute.Home.route) { inclusive = true }
                    }
                },
                onNavigateBottomNav = { item ->
                    if (item.route != ScreenRoute.Settings.route) {
                        navController.navigate(item.route) {
                            popUpTo(ScreenRoute.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
