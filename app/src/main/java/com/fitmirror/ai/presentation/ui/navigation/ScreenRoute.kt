package com.fitmirror.ai.presentation.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash_screen")
    object Onboarding : ScreenRoute("onboarding_screen")
    object Auth : ScreenRoute("auth_screen")
    object Home : ScreenRoute("home_screen")
    object Upload : ScreenRoute("upload_screen/{clothingId}") {
        fun createRoute(clothingId: String = "none"): String {
            return "upload_screen/$clothingId"
        }
    }
    object Generating : ScreenRoute("generating_screen/{clothingId}/{quality}") {
        fun createRoute(clothingId: String, qualityName: String): String {
            return "generating_screen/$clothingId/$qualityName"
        }
    }
    object Results : ScreenRoute("results_screen/{resultId}") {
        fun createRoute(resultId: String = "latest"): String {
            return "results_screen/$resultId"
        }
    }
    object History : ScreenRoute("history_screen")
    object Subscription : ScreenRoute("subscription_screen")
    object Settings : ScreenRoute("settings_screen")
}
