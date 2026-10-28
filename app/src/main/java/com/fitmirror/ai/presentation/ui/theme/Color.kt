package com.fitmirror.ai.presentation.ui.theme

import androidx.compose.ui.graphics.Color

// ===================================================================
//  FitMirror AI — Editorial Minimal Palette
//  Warm ivory + ink + stone, inspired by Zara / COS / SSENSE lookbooks.
//  Legacy color names are preserved as aliases so all screens compile,
//  while their values now resolve to the new editorial system.
// ===================================================================

// ---- Primary Ink (replaces Deep Purple) ----
val DeepPurple = Color(0xFF16150F)      // Warm near-black ink — primary action & text
val DeepPurpleDark = Color(0xFF000000)  // True black
val DeepPurpleLight = Color(0xFF6E6859) // Warm taupe — dark-theme primary

// ---- Accent (replaces Electric Blue) ----
val ElectricBlue = Color(0xFFB4573A)    // Muted terracotta — the single warm accent
val ElectricBlueAccent = Color(0xFFC47A5A) // Lighter terracotta
val ElectricBlueMuted = Color(0xFFD9B48F)  // Soft sand accent

// ---- Background & Surface ----
val PureWhite = Color(0xFFFFFFFF)
val PureBlack = Color(0xFF000000)
val SoftWhite = Color(0xFFF7F5F0)       // Warm ivory background
val LuxuryDarkBackground = Color(0xFF121110) // Warm night background
val LuxuryDarkCard = Color(0xFF1C1A16)  // Warm dark surface

// ---- Secondary & Neutrals ----
val LightGray = Color(0xFFE6E2D8)       // Warm hairline border
val DarkCharcoal = Color(0xFF16150F)    // Warm ink text
val MediumGray = Color(0xFF8C887A)      // Warm stone — tertiary text
val DarkGrayBorder = Color(0xFF2E2B24)  // Warm dark border

// ---- Success, Warning, Error ----
val LuxuryGold = Color(0xFFB08D57)      // VIP / annual badge
val FitMatchGreen = Color(0xFF3E7A5E)   // Muted deep green — fit match
val ErrorRed = Color(0xFFB3382E)        // Muted editorial red

// ===================================================================
//  Semantic aliases (preferred in new code)
// ===================================================================
val Ink = DeepPurple
val InkSoft = DeepPurpleLight
val Stone = MediumGray
val Hairline = LightGray
val Terracotta = ElectricBlue
val TerracottaSoft = ElectricBlueAccent
val Ivory = SoftWhite
val EditorialWhite = PureWhite
val Night = LuxuryDarkBackground
val NightSurface = LuxuryDarkCard
val NightHairline = DarkGrayBorder
val NightIvory = Color(0xFFF2EFE8)      // Warm off-white text on dark surfaces
