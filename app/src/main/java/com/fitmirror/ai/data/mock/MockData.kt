package com.fitmirror.ai.data.mock

import com.fitmirror.ai.R
import com.fitmirror.ai.domain.model.ClothingItem
import com.fitmirror.ai.domain.model.MembershipTier
import com.fitmirror.ai.domain.model.OnboardingPage
import com.fitmirror.ai.domain.model.QualityOption
import com.fitmirror.ai.domain.model.SubscriptionPlan
import com.fitmirror.ai.domain.model.TryOnResult
import com.fitmirror.ai.domain.model.User

object MockData {

    val sampleUser = User(
        id = "user_elena_001",
        name = "Elena Rostova",
        email = "elena.rostova@luxury.com",
        avatarRes = R.drawable.sample_model_female,
        membershipTier = MembershipTier.PRO,
        remainingCredits = 12,
        maxCredits = 15,
        isGuest = false,
        totalTryOns = 24
    )

    val clothingCatalog = listOf(
        ClothingItem(
            id = "cloth_trench_01",
            title = "Silk Trench Coat",
            brand = "COS",
            category = "Outerwear",
            imageRes = R.drawable.sample_clothing_trench,
            season = "Autumn / Winter",
            description = "Deconstructed double-breasted trench coat tailored in luxurious beige mulberry silk blend with fluid drape.",
            price = "$280"
        ),
        ClothingItem(
            id = "cloth_dress_02",
            title = "Emerald Silk Evening Dress",
            brand = "ZARA STUDIO",
            category = "Evening Wear",
            imageRes = R.drawable.sample_clothing_dress,
            season = "High Summer",
            description = "Minimalist bias-cut slip dress crafted from 100% emerald green hammered silk with delicate rouleau straps.",
            price = "$195"
        ),
        ClothingItem(
            id = "cloth_suit_03",
            title = "Tailored Wool Suit",
            brand = "COS ATELIER",
            category = "Tailoring",
            imageRes = R.drawable.sample_clothing_suit,
            season = "All Season",
            description = "Architectural single-breasted wool blazer and relaxed wide-leg trouser set in midnight navy.",
            price = "$450"
        )
    )

    val initialHistory = listOf(
        TryOnResult(
            id = "result_001",
            title = "Silk Trench Coat",
            brand = "COS",
            category = "Outerwear",
            userImageRes = R.drawable.sample_model_female,
            clothingImageRes = R.drawable.sample_clothing_trench,
            frontResultRes = R.drawable.sample_result_trench_front,
            backResultRes = R.drawable.sample_result_trench_back,
            fitScore = 98,
            date = "Today, 18:45",
            isFavorite = true,
            quality = QualityOption.HD,
            notes = "Silk drape and tailored shoulder silhouette matched perfectly to your posture."
        ),
        TryOnResult(
            id = "result_002",
            title = "Emerald Silk Evening Dress",
            brand = "ZARA STUDIO",
            category = "Evening Wear",
            userImageRes = R.drawable.sample_model_female,
            clothingImageRes = R.drawable.sample_clothing_dress,
            frontResultRes = R.drawable.sample_result_dress_front,
            backResultRes = R.drawable.sample_result_dress_front,
            fitScore = 96,
            date = "Yesterday, 14:20",
            isFavorite = true,
            quality = QualityOption.HD,
            notes = "Waistline bias cut aligns effortlessly with natural silhouette."
        ),
        TryOnResult(
            id = "result_003",
            title = "Tailored Wool Suit",
            brand = "COS ATELIER",
            category = "Tailoring",
            userImageRes = R.drawable.sample_model_male,
            clothingImageRes = R.drawable.sample_clothing_suit,
            frontResultRes = R.drawable.sample_result_suit_front,
            backResultRes = R.drawable.sample_result_suit_front,
            fitScore = 99,
            date = "Jul 28, 19:10",
            isFavorite = false,
            quality = QualityOption.ULTRA_4K,
            notes = "Shoulder seam and lapel drape match editorial tailoring proportions."
        )
    )

    val subscriptionPlans = listOf(
        SubscriptionPlan(
            id = "plan_free",
            name = "Free",
            tagline = "Essential styling exploration",
            monthlyPrice = "$0",
            annualPrice = "$0",
            features = listOf(
                "5 Try-ons per month",
                "Standard Quality (1080p)",
                "Basic Wardrobe Catalog",
                "Community Style Feed"
            ),
            isPopular = false,
            isCurrentPlan = false,
            tier = MembershipTier.FREE
        ),
        SubscriptionPlan(
            id = "plan_pro",
            name = "Pro",
            tagline = "The signature luxury try-on studio",
            monthlyPrice = "$14.99",
            annualPrice = "$144.00",
            features = listOf(
                "Unlimited AI Try-ons",
                "HD Quality Editorial Render",
                "Priority Fast Generation",
                "No Watermark on Downloads",
                "Full Designer Brand Catalog"
            ),
            isPopular = true,
            isCurrentPlan = true,
            tier = MembershipTier.PRO
        ),
        SubscriptionPlan(
            id = "plan_plus",
            name = "Plus",
            tagline = "For styling connoisseurs & creators",
            monthlyPrice = "$29.99",
            annualPrice = "$288.00",
            features = listOf(
                "4K Ultra-HD Editorial Quality",
                "Custom Digital Lookbook Export",
                "Dedicated Personal AI Stylist",
                "Early Access to Runway Collections",
                "24/7 VIP Concierge Support"
            ),
            isPopular = false,
            isCurrentPlan = false,
            tier = MembershipTier.PLUS
        )
    )

    val onboardingPages = listOf(
        OnboardingPage(
            title = "See Yourself Before Buying",
            subtitle = "Preview outfits before spending money.",
            points = listOf(
                "High-fidelity AI drape simulation",
                "Virtual mirrors for luxury fashion",
                "Zero guesswork when shopping"
            ),
            buttonText = "Continue",
            illustrationTag = "onboarding_1"
        ),
        OnboardingPage(
            title = "How FitMirror AI Works",
            subtitle = "Three effortless steps to your next signature look.",
            points = listOf(
                "Upload yourself.",
                "Upload clothing.",
                "Generate realistic previews."
            ),
            buttonText = "Continue",
            illustrationTag = "onboarding_2"
        ),
        OnboardingPage(
            title = "Discover Your Signature Style",
            subtitle = "Elevate your personal aesthetic effortlessly.",
            points = listOf(
                "Discover your style.",
                "Save money.",
                "Avoid bad purchases."
            ),
            buttonText = "Get Started",
            illustrationTag = "onboarding_3"
        )
    )
}
