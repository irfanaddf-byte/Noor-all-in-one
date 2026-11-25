package com.example.ui.components

import com.example.data.model.AppLanguage

object NoorStrings {
    fun home(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "होम"
        AppLanguage.URDU -> "ہوم"
        AppLanguage.ENGLISH -> "Home"
    }

    fun quran(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "क़ुरआन"
        AppLanguage.URDU -> "قرآن"
        AppLanguage.ENGLISH -> "Quran"
    }

    fun namaz(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "नमाज़"
        AppLanguage.URDU -> "نماز"
        AppLanguage.ENGLISH -> "Namaz"
    }

    fun tasbeeh(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "तसबीह"
        AppLanguage.URDU -> "تسبیح"
        AppLanguage.ENGLISH -> "Tasbeeh"
    }

    fun more(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "और देखें"
        AppLanguage.URDU -> "مزید"
        AppLanguage.ENGLISH -> "More"
    }

    fun greetingPrefix(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "अस्सलामु अलैकुम भाईजान"
        AppLanguage.URDU -> "السلام علیکم بھائی جان"
        AppLanguage.ENGLISH -> "Assalamualaikum Bhaijaan"
    }

    fun nextPrayerLabel(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "अगली नमाज़"
        AppLanguage.URDU -> "اگلی نماز"
        AppLanguage.ENGLISH -> "Next Prayer"
    }

    fun todaysQuoteTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "आज का नूरी पैग़ाम"
        AppLanguage.URDU -> "آج کا پیغام"
        AppLanguage.ENGLISH -> "Aaj Ka Quote"
    }

    fun featuresTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "सभी इस्लामी फ़ीचर्स"
        AppLanguage.URDU -> "تمام اسلامی فیچرز"
        AppLanguage.ENGLISH -> "Islamic Features"
    }

    fun dailyGoalTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.HINDI -> "रोज़ाना इबादत लक्ष्य"
        AppLanguage.URDU -> "روزانہ عبادت کا ہدف"
        AppLanguage.ENGLISH -> "Daily Ibadat Goal"
    }
}
