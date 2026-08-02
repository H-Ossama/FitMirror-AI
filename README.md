# FitMirror AI — See Yourself Before You Buy. ✨

<div align="center">

**A Production-Quality Luxury Fashion Virtual Try-On Android Application**

*Built with Android, Kotlin, Jetpack Compose, Material 3, Clean Architecture, MVVM, Hilt, Coroutines & StateFlow.*

</div>

---

## 💎 Project Mission & Philosophy

**FitMirror AI** is an AI-powered fashion application that allows users to upload their full-body picture and a clothing image to generate a realistic image of themselves wearing those clothes.

> **Note:** This standalone frontend release operates entirely with **high-fidelity luxury mock data** and **real AI-generated editorial fashion photography assets**, requiring zero backend, API, or external server dependencies.

---

## 🎨 Design System & Luxury Aesthetics

Inspired by the tactile minimalism of **Apple Liquid Glass**, **Notion**, **Pinterest**, **Zara**, **COS**, and **H&M**, the FitMirror AI interface feels:
- **Luxury** & **Minimal**
- **Modern** & **Elegant**
- **Soft** & **Clean**

### ✨ Visual Signatures
- **Subtle Glassmorphism:** Translucent glass cards (`Modifier.glassmorphic(...)`) with multi-stop border shimmer gradients.
- **Large White Spacing:** Generous whitespace (`LuxuryDimensions`) to prevent crowded layouts and emphasize high-fashion imagery.
- **Responsive Typography:** Clean sans-serif editorial typography (`LuxuryTypography`) with negative letter spacing for display titles.
- **Tactile Animations:** Every button features a spring-physics press scale animation (`Modifier.pressScale(...)`), and every screen transition utilizes smooth directional slides and fades.
- **Dynamic Themes:** Native support for **Luxury Light** (Soft White & Charcoal) and **Pure Black** (OLED Dark Mode with Deep Purple & Electric Blue accents).

---

## 🌈 Color Palette

| Name | Hex Code | Role |
| :--- | :--- | :--- |
| **Deep Purple** | `#4A154B` / `#6C2BD9` | Primary Brand & Luxury Gradient Core |
| **Electric Blue** | `#00F0FF` / `#2D7EFF` | Accent & Interactive Match Highlights |
| **Soft White** | `#F8F9FB` | Light Mode Cards & Surfaces |
| **Pure Black** | `#000000` / `#0A0A0E` | Dark Mode Backgrounds & Modal Overlays |
| **Luxury Dark Card** | `#13131A` | OLED Glass Surface in Dark Theme |
| **Dark Charcoal** | `#1A1A24` | Primary Text in Light Theme |

---

## 📱 Comprehensive 10-Screen Flow

```
[ Splash ]
    ↓
[ Onboarding (3 Pages) ]
    ↓
[ Authentication ]
    ↓
[ Home ]  ───→  [ Upload ]  ───→  [ Generating ]  ───→  [ Results ]
    │                                                        │
    ├───→  [ History ]   <───────────────────────────────────┘
    │
    ├───→  [ Subscription ]
    │
    └───→  [ Settings ]
```

### Screen Breakdown
1. **Splash (`SplashScreen.kt`)**: Monogram logo with smooth scale/alpha pulse and luxury hero gradient background.
2. **Onboarding (`OnboardingScreen.kt`)**: 3-page `HorizontalPager` with bespoke glass card previews and animated pill indicators.
3. **Authentication (`AuthScreen.kt`)**: Centered luxury minimal login for Google, Apple, Email, and Guest Mode with interactive Privacy Policy and Terms modals.
4. **Home (`HomeScreen.kt`)**: Dynamic greeting based on time of day, remaining credits badge, active subscription status pill, horizontal recent try-ons gallery, and curated high-fashion catalog.
5. **Upload (`UploadScreen.kt`)**: Large interactive upload cards ("Your Photo" & "Clothing Image") with avatar/garment selectors, checkmark requirements checklist (`✔ Full Body`, `✔ Standing Position`, etc.), and quality selectors (`Standard`, `HD`, `4K`).
6. **Generating (`GeneratingScreen.kt`)**: Geometric pulse loader (`LuxuryPulseLoader`), animated status cycler (`Finding body...` → `Analyzing clothes...` → `Matching pose...` → `Generating...`), and smooth progress bar.
7. **Results (`ResultsScreen.kt`)**: Interactive split-screen **Before / After swipe comparison slider** (`ComparisonSlider`), Front / Back view selector, fit score badge (`98% Match`), and quick actions (`Save`, `Download`, `Share`, `Generate Again`, `Try Another Outfit`).
8. **History (`HistoryScreen.kt`)**: Responsive 2-column grid of previous looks with instant search filtering, favorite toggles, and destructive confirmation dialogs (`ConfirmDialog`).
9. **Subscription (`SubscriptionScreen.kt`)**: Pricing tiers (`Free`, `Pro`, `Plus`) with highlighted electric blue and gold shimmer card for the popular `Pro` plan, plus Monthly / Annual billing toggle (`Save 20%`).
10. **Settings (`SettingsScreen.kt`)**: Profile summary, theme selector (`System`, `Light`, `Dark`), language switcher (`English`, `French`, `Italian`, `Japanese`, `Spanish`), push notification switch, privacy policy/terms modals, and account deletion dialog.

---

## 🏗️ Architectural Overview (Clean Architecture + MVVM + Hilt)

The codebase strictly adheres to modular **Clean Architecture**, enforcing unidirectional data flow across three layers:

```
app/src/main/java/com/fitmirror/ai/
├── presentation/
│   ├── ui/
│   │   ├── components/      # Reusable Luxury UI components (Buttons, Cards, Modals, Sliders, Bars)
│   │   ├── screens/         # All 10 application screens organized by domain feature
│   │   ├── navigation/      # Sealed routes & Navigation Compose transition graph
│   │   └── theme/           # Color, Shape, Typography, Dimensions & CompositionLocals
│   └── viewmodels/          # Hilt-injected MVVM ViewModels with reactive StateFlows
├── domain/
│   ├── model/               # Immutable domain entities (User, TryOnResult, ClothingItem, SubscriptionPlan)
│   ├── repository/          # Decoupled repository interfaces
│   └── usecase/             # Business logic orchestrators (GetHomeDataUseCase, GenerateTryOnUseCase, etc.)
├── data/
│   ├── mock/                # Standalone production-grade fake dataset & resources
│   └── repository/          # Stateful mock implementations with cross-screen synchronization
├── di/
│   └── AppModule.kt         # Dagger-Hilt DI bindings for repositories & use cases
└── utils/
    ├── Glassmorphism.kt     # Apple Liquid Glass modifiers & border gradient brushes
    ├── AnimationUtils.kt    # Spring-physics button scaling & transition specs
    └── WindowSizeUtils.kt   # Responsive layout helpers for phones, foldables & tablets
```

---

## 🚀 Reusable Component Library (`presentation/ui/components/`)

- `FitMirrorPrimaryButton`, `FitMirrorSecondaryButton`, `FitMirrorHeroButton`: Interactive buttons with spring physics press scale (`Modifier.pressScale()`).
- `LuxuryCard`, `ElevatedLuxuryCard`: Apple Liquid Glass surface cards with border sheen (`Modifier.glassmorphic()`).
- `ComparisonSlider`: Custom interactive touch-drag slider for Before/After split image wipes and Front/Back toggle pills.
- `UploadImageCard`, `RecentTryOnCard`, `CatalogClothingCard`: High-fashion editorial image cards with badges.
- `LuxuryPulseLoader`, `ScannerAnimation`, `ShimmerSkeleton`: Custom Canvas-based loading animations.
- `FitMirrorTopAppBar`, `FitMirrorBottomNav`: Translucent headers and floating pill bottom navigation bars with animated selection state.
- `ConfirmDialog`, `InfoModal`: Reusable luxury modal dialogues for destructive actions and legal/about documents.
- `AnimatedStatusCycler`, `LuxuryProgressBar`, `PageIndicator`: Animated progress and step indicators.

---

## 🛠️ Tech Stack & Dependencies

- **Language:** Kotlin 1.9.22
- **UI Toolkit:** Jetpack Compose (BOM 2024.02.00) + Material 3
- **Architecture:** MVVM + Clean Architecture + StateFlow
- **Navigation:** Navigation Compose 2.7.7 with custom screen transitions
- **Dependency Injection:** Dagger Hilt 2.50
- **Asynchronous & State:** Kotlinx Coroutines 1.8.0 & StateFlow
- **Image Loading:** Coil Compose 2.6.0 (supporting both local `@DrawableRes` and remote URLs)

---

## 💡 How to Build & Run

1. Open Android Studio (Ladybug / Iguana or newer).
2. Open the cloned root directory (`/home/user/FitMirror-AI`).
3. Sync Gradle and wait for Dagger-Hilt KSP code generation to complete.
4. Select the `app` configuration and click **Run** on any Android 8.0+ (API 26+) emulator or physical device.
5. Experience the luxury virtual try-on frontend without needing an active backend!
