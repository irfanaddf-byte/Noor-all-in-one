package com.example.data.model

import androidx.annotation.DrawableRes

enum class AppLanguage(val label: String, val nativeLabel: String) {
    HINDI("Hindi", "हिंदी"),
    URDU("Urdu", "اردو"),
    ENGLISH("English", "English")
}

data class CityInfo(
    val name: String,
    val displaySub: String,
    val lat: Double,
    val lon: Double,
    val weatherTemp: String,
    val weatherCond: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val sehri: String,
    val iftar: String
)

data class PrayerEntry(
    val key: String,
    val nameHindi: String,
    val nameUrdu: String,
    val nameEnglish: String,
    val time24: String,
    val timeDisplay: String
)

data class QuranVerse(
    val number: Int,
    val arabic: String,
    val hindiTarjuma: String,
    val urduTarjuma: String,
    val englishTarjuma: String
)

data class SurahInfo(
    val number: Int,
    val name: String,
    val arabicName: String,
    val ayahsCount: Int,
    val revelationType: String, // "Makki" or "Madani"
    val meaningHindi: String,
    val verses: List<QuranVerse>
)

data class DuaItem(
    val id: Int,
    val title: String,
    val category: String, // Rozmarra, Subah, Sham, Jummah, Safar, Ibadat
    val arabic: String,
    val transliteration: String,
    val hindiTarjuma: String,
    val urduTarjuma: String,
    val englishTarjuma: String
)

data class HadithItem(
    val id: Int,
    val arabic: String,
    val textHindi: String,
    val textUrdu: String,
    val textEnglish: String,
    val reference: String
)

data class AllahName(
    val number: Int,
    val arabic: String,
    val transliteration: String,
    val hindiMeaning: String,
    val benefitHindi: String
)

data class IslamicEvent(
    val title: String,
    val hijriDate: String,
    val gregorianDate: String,
    val description: String,
    val highlight: Boolean = false
)

data class NaatItem(
    val id: Int,
    val title: String,
    val artist: String,
    val type: String, // "Naat" or "Bayan"
    val durationSec: Int,
    val recitationText: String
)

data class MasjidItem(
    val id: Int,
    val name: String,
    val city: String,
    val area: String,
    val distanceKm: String,
    val fajrJamaat: String,
    val dhuhrJamaat: String,
    val asrJamaat: String,
    val maghribJamaat: String,
    val ishaJamaat: String,
    val jummahJamaat: String,
    val lat: Double,
    val lon: Double
)

data class WallpaperItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    @DrawableRes val drawableRes: Int? = null,
    val primaryHex: Long = 0xFF0B3D2E,
    val secondaryHex: Long = 0xFF062118,
    val accentHex: Long = 0xFFC9A227,
    val calligraphy: String = "اللَّهُ نُورُ السَّمَاوَاتِ وَالْأَرْضِ"
)

data class TasbeehZikr(
    val id: String,
    val title: String,
    val arabic: String,
    val meaning: String
)

data class TasbeehHistoryEntry(
    val zikrTitle: String,
    val count: Int,
    val timestampLabel: String
)

data class KalmaItem(
    val number: Int,
    val title: String,
    val arabic: String,
    val hindiTranslit: String,
    val hindiMeaning: String
)

data class ProphetStory(
    val id: Int,
    val title: String,
    val subtitle: String,
    val storyHindi: String,
    val moralHindi: String
)

data class FaqItem(
    val question: String,
    val answer: String
)

data class DailyQuote(
    val textHindi: String,
    val textUrdu: String,
    val textEnglish: String,
    val source: String
)

data class ZakatResult(
    val totalWealth: Double,
    val goldValue: Double,
    val silverValue: Double,
    val nisabThreshold: Double,
    val isFarz: Boolean,
    val zakatAmount: Double,
    val explanationHindi: String
)
