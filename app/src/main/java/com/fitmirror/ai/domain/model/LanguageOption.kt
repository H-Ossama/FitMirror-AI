package com.fitmirror.ai.domain.model

enum class LanguageOption(val code: String, val label: String, val nativeName: String) {
    ENGLISH("en", "English", "English (UK/US)"),
    FRENCH("fr", "French", "Français"),
    ITALIAN("it", "Italian", "Italiano"),
    JAPANESE("ja", "Japanese", "日本語"),
    SPANISH("es", "Spanish", "Español")
}
