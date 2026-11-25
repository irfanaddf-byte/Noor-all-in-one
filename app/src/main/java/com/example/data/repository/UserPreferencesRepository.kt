package com.example.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.model.AppLanguage
import com.example.data.model.TasbeehHistoryEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "noor_prefs")

data class UserPrefsSnapshot(
    val userName: String = "",
    val userPhotoUri: String = "",
    val onboardingCompleted: Boolean = false,
    val selectedCityName: String = "Thakurdwara",
    val useGpsLocation: Boolean = true,
    val darkMode: Boolean = false,
    val fontScale: Float = 1.0f,
    val language: AppLanguage = AppLanguage.HINDI,
    val vibrationEnabled: Boolean = true,
    val morningHadithNotif: Boolean = true,
    val azanAlarms: Set<String> = setOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"),
    val lastReadSurah: Int = 1,
    val bookmarkedSurahs: Set<Int> = emptySet(),
    val favouriteDuas: Set<Int> = emptySet(),
    val favouriteHadiths: Set<Int> = emptySet(),
    val favouriteNames: Set<Int> = emptySet(),
    val tasbeehCount: Int = 0,
    val tasbeehZikrId: String = "subhanallah",
    val tasbeehTarget: Int = 100,
    val tasbeehTodayTotal: Int = 0,
    val tasbeehHistory: List<TasbeehHistoryEntry> = emptyList(),
    val todayPrayedDate: String = "",
    val todayPrayedSet: Set<String> = emptySet(),
    val qazaFajr: Int = 0,
    val qazaDhuhr: Int = 0,
    val qazaAsr: Int = 0,
    val qazaMaghrib: Int = 0,
    val qazaIsha: Int = 0,
    val rozaDaysCompleted: Set<Int> = emptySet(),
    val rozaNafliCount: Int = 0,
    val rozaHistory: List<String> = emptyList(),
    val dailyGoalTarget: Int = 100,
    val streakDays: Int = 1,
    val lastActiveDate: String = ""
)

class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PHOTO = stringPreferencesKey("user_photo_uri")
        val ONBOARDING = booleanPreferencesKey("onboarding_completed")
        val CITY_NAME = stringPreferencesKey("selected_city")
        val USE_GPS = booleanPreferencesKey("use_gps")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val FONT_SCALE = floatPreferencesKey("font_scale")
        val LANGUAGE = stringPreferencesKey("language")
        val VIBRATION = booleanPreferencesKey("vibration_enabled")
        val MORNING_HADITH = booleanPreferencesKey("morning_hadith_notif")
        val AZAN_ALARMS = stringSetPreferencesKey("azan_alarms")
        val LAST_SURAH = intPreferencesKey("last_read_surah")
        val BOOKMARK_SURAHS = stringSetPreferencesKey("bookmarked_surahs")
        val FAV_DUAS = stringSetPreferencesKey("fav_duas")
        val FAV_HADITHS = stringSetPreferencesKey("fav_hadiths")
        val FAV_NAMES = stringSetPreferencesKey("fav_names")
        val TASBEEH_COUNT = intPreferencesKey("tasbeeh_count")
        val TASBEEH_ZIKR = stringPreferencesKey("tasbeeh_zikr")
        val TASBEEH_TARGET = intPreferencesKey("tasbeeh_target")
        val TASBEEH_TODAY = intPreferencesKey("tasbeeh_today_total")
        val TASBEEH_DATE = stringPreferencesKey("tasbeeh_date")
        val TASBEEH_HISTORY = stringPreferencesKey("tasbeeh_history_serialized")
        val TODAY_PRAYED_DATE = stringPreferencesKey("today_prayed_date")
        val TODAY_PRAYED_SET = stringSetPreferencesKey("today_prayed_set")
        val QAZA_FAJR = intPreferencesKey("qaza_fajr")
        val QAZA_DHUHR = intPreferencesKey("qaza_dhuhr")
        val QAZA_ASR = intPreferencesKey("qaza_asr")
        val QAZA_MAGHRIB = intPreferencesKey("qaza_maghrib")
        val QAZA_ISHA = intPreferencesKey("qaza_isha")
        val ROZA_DAYS = stringSetPreferencesKey("roza_days")
        val ROZA_NAFLI = intPreferencesKey("roza_nafli")
        val ROZA_HISTORY = stringPreferencesKey("roza_history_serialized")
        val DAILY_GOAL = intPreferencesKey("daily_goal_target")
        val STREAK_DAYS = intPreferencesKey("streak_days")
        val LAST_ACTIVE_DATE = stringPreferencesKey("last_active_date")
    }

    val prefsFlow: Flow<UserPrefsSnapshot> = context.dataStore.data.map { p ->
        val historyStr = p[Keys.TASBEEH_HISTORY] ?: ""
        val history = if (historyStr.isBlank()) emptyList() else historyStr.split(";;").mapNotNull { entry ->
            val parts = entry.split("|")
            if (parts.size == 3) TasbeehHistoryEntry(parts[0], parts[1].toIntOrNull() ?: 0, parts[2]) else null
        }
        val rozaHistStr = p[Keys.ROZA_HISTORY] ?: ""
        val rozaHist = if (rozaHistStr.isBlank()) emptyList() else rozaHistStr.split(";;").filter { it.isNotBlank() }

        UserPrefsSnapshot(
            userName = p[Keys.USER_NAME] ?: "",
            userPhotoUri = p[Keys.USER_PHOTO] ?: "",
            onboardingCompleted = p[Keys.ONBOARDING] ?: false,
            selectedCityName = p[Keys.CITY_NAME] ?: "Thakurdwara",
            useGpsLocation = p[Keys.USE_GPS] ?: true,
            darkMode = p[Keys.DARK_MODE] ?: false,
            fontScale = p[Keys.FONT_SCALE] ?: 1.0f,
            language = runCatching { AppLanguage.valueOf(p[Keys.LANGUAGE] ?: "HINDI") }.getOrDefault(AppLanguage.HINDI),
            vibrationEnabled = p[Keys.VIBRATION] ?: true,
            morningHadithNotif = p[Keys.MORNING_HADITH] ?: true,
            azanAlarms = p[Keys.AZAN_ALARMS] ?: setOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"),
            lastReadSurah = p[Keys.LAST_SURAH] ?: 1,
            bookmarkedSurahs = (p[Keys.BOOKMARK_SURAHS] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet(),
            favouriteDuas = (p[Keys.FAV_DUAS] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet(),
            favouriteHadiths = (p[Keys.FAV_HADITHS] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet(),
            favouriteNames = (p[Keys.FAV_NAMES] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet(),
            tasbeehCount = p[Keys.TASBEEH_COUNT] ?: 0,
            tasbeehZikrId = p[Keys.TASBEEH_ZIKR] ?: "subhanallah",
            tasbeehTarget = p[Keys.TASBEEH_TARGET] ?: 100,
            tasbeehTodayTotal = p[Keys.TASBEEH_TODAY] ?: 0,
            tasbeehHistory = history,
            todayPrayedDate = p[Keys.TODAY_PRAYED_DATE] ?: "",
            todayPrayedSet = p[Keys.TODAY_PRAYED_SET] ?: emptySet(),
            qazaFajr = p[Keys.QAZA_FAJR] ?: 0,
            qazaDhuhr = p[Keys.QAZA_DHUHR] ?: 0,
            qazaAsr = p[Keys.QAZA_ASR] ?: 0,
            qazaMaghrib = p[Keys.QAZA_MAGHRIB] ?: 0,
            qazaIsha = p[Keys.QAZA_ISHA] ?: 0,
            rozaDaysCompleted = (p[Keys.ROZA_DAYS] ?: emptySet()).mapNotNull { it.toIntOrNull() }.toSet(),
            rozaNafliCount = p[Keys.ROZA_NAFLI] ?: 0,
            rozaHistory = rozaHist,
            dailyGoalTarget = p[Keys.DAILY_GOAL] ?: 100,
            streakDays = p[Keys.STREAK_DAYS] ?: 1,
            lastActiveDate = p[Keys.LAST_ACTIVE_DATE] ?: ""
        )
    }

    suspend fun saveUserNameAndCompleteOnboarding(name: String) {
        context.dataStore.edit {
            it[Keys.USER_NAME] = name.trim()
            it[Keys.ONBOARDING] = true
        }
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { it[Keys.USER_NAME] = name.trim() }
    }

    suspend fun updateUserPhoto(uri: String) {
        context.dataStore.edit { it[Keys.USER_PHOTO] = uri }
    }

    suspend fun setSelectedCity(cityName: String) {
        context.dataStore.edit { it[Keys.CITY_NAME] = cityName }
    }

    suspend fun setUseGps(enabled: Boolean) {
        context.dataStore.edit { it[Keys.USE_GPS] = enabled }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setFontScale(scale: Float) {
        context.dataStore.edit { it[Keys.FONT_SCALE] = scale }
    }

    suspend fun setLanguage(lang: AppLanguage) {
        context.dataStore.edit { it[Keys.LANGUAGE] = lang.name }
    }

    suspend fun setVibration(enabled: Boolean) {
        context.dataStore.edit { it[Keys.VIBRATION] = enabled }
    }

    suspend fun setMorningHadithNotif(enabled: Boolean) {
        context.dataStore.edit { it[Keys.MORNING_HADITH] = enabled }
    }

    suspend fun toggleAzanAlarm(prayerKey: String) {
        context.dataStore.edit {
            val current = (it[Keys.AZAN_ALARMS] ?: setOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")).toMutableSet()
            if (current.contains(prayerKey)) current.remove(prayerKey) else current.add(prayerKey)
            it[Keys.AZAN_ALARMS] = current
        }
    }

    suspend fun setLastReadSurah(surahNumber: Int) {
        context.dataStore.edit { it[Keys.LAST_SURAH] = surahNumber }
    }

    suspend fun toggleSurahBookmark(surahNumber: Int) {
        context.dataStore.edit {
            val set = (it[Keys.BOOKMARK_SURAHS] ?: emptySet()).toMutableSet()
            val key = surahNumber.toString()
            if (set.contains(key)) set.remove(key) else set.add(key)
            it[Keys.BOOKMARK_SURAHS] = set
        }
    }

    suspend fun toggleFavouriteDua(id: Int) {
        context.dataStore.edit {
            val set = (it[Keys.FAV_DUAS] ?: emptySet()).toMutableSet()
            val key = id.toString()
            if (set.contains(key)) set.remove(key) else set.add(key)
            it[Keys.FAV_DUAS] = set
        }
    }

    suspend fun toggleFavouriteHadith(id: Int) {
        context.dataStore.edit {
            val set = (it[Keys.FAV_HADITHS] ?: emptySet()).toMutableSet()
            val key = id.toString()
            if (set.contains(key)) set.remove(key) else set.add(key)
            it[Keys.FAV_HADITHS] = set
        }
    }

    suspend fun toggleFavouriteName(number: Int) {
        context.dataStore.edit {
            val set = (it[Keys.FAV_NAMES] ?: emptySet()).toMutableSet()
            val key = number.toString()
            if (set.contains(key)) set.remove(key) else set.add(key)
            it[Keys.FAV_NAMES] = set
        }
    }

    suspend fun updateTasbeehTap(newCount: Int, newTodayTotal: Int, todayStr: String) {
        context.dataStore.edit {
            it[Keys.TASBEEH_COUNT] = newCount
            it[Keys.TASBEEH_TODAY] = newTodayTotal
            it[Keys.TASBEEH_DATE] = todayStr
        }
    }

    suspend fun setTasbeehZikr(zikrId: String) {
        context.dataStore.edit { it[Keys.TASBEEH_ZIKR] = zikrId }
    }

    suspend fun setTasbeehTarget(target: Int) {
        context.dataStore.edit { it[Keys.TASBEEH_TARGET] = target }
    }

    suspend fun saveAndResetTasbeeh(zikrTitle: String, count: Int, timestamp: String) {
        context.dataStore.edit {
            if (count > 0) {
                val existing = (it[Keys.TASBEEH_HISTORY] ?: "")
                val newEntry = "$zikrTitle|$count|$timestamp"
                val combined = if (existing.isBlank()) newEntry else "$newEntry;;$existing"
                it[Keys.TASBEEH_HISTORY] = combined.split(";;").take(25).joinToString(";;")
            }
            it[Keys.TASBEEH_COUNT] = 0
        }
    }

    suspend fun toggleTodayPrayer(prayerKey: String, todayDate: String) {
        context.dataStore.edit {
            val prevDate = it[Keys.TODAY_PRAYED_DATE] ?: ""
            val current = if (prevDate == todayDate) {
                (it[Keys.TODAY_PRAYED_SET] ?: emptySet()).toMutableSet()
            } else {
                mutableSetOf()
            }
            if (current.contains(prayerKey)) current.remove(prayerKey) else current.add(prayerKey)
            it[Keys.TODAY_PRAYED_DATE] = todayDate
            it[Keys.TODAY_PRAYED_SET] = current
        }
    }

    suspend fun adjustQaza(prayerKey: String, delta: Int) {
        context.dataStore.edit {
            val key = when (prayerKey) {
                "Fajr" -> Keys.QAZA_FAJR
                "Dhuhr" -> Keys.QAZA_DHUHR
                "Asr" -> Keys.QAZA_ASR
                "Maghrib" -> Keys.QAZA_MAGHRIB
                else -> Keys.QAZA_ISHA
            }
            val current = it[key] ?: 0
            it[key] = (current + delta).coerceAtLeast(0)
        }
    }

    suspend fun toggleRozaDay(day: Int, historyNote: String) {
        context.dataStore.edit {
            val set = (it[Keys.ROZA_DAYS] ?: emptySet()).toMutableSet()
            val key = day.toString()
            val added = if (set.contains(key)) {
                set.remove(key)
                false
            } else {
                set.add(key)
                true
            }
            it[Keys.ROZA_DAYS] = set
            if (added) {
                val prev = it[Keys.ROZA_HISTORY] ?: ""
                val next = if (prev.isBlank()) historyNote else "$historyNote;;$prev"
                it[Keys.ROZA_HISTORY] = next.split(";;").take(35).joinToString(";;")
            }
        }
    }

    suspend fun addNafliRoza(historyNote: String) {
        context.dataStore.edit {
            it[Keys.ROZA_NAFLI] = (it[Keys.ROZA_NAFLI] ?: 0) + 1
            val prev = it[Keys.ROZA_HISTORY] ?: ""
            val next = if (prev.isBlank()) historyNote else "$historyNote;;$prev"
            it[Keys.ROZA_HISTORY] = next.split(";;").take(35).joinToString(";;")
        }
    }

    suspend fun touchDailyStreak(todayStr: String, yesterdayStr: String) {
        context.dataStore.edit {
            val last = it[Keys.LAST_ACTIVE_DATE] ?: ""
            if (last != todayStr) {
                val currentStreak = it[Keys.STREAK_DAYS] ?: 1
                it[Keys.STREAK_DAYS] = if (last == yesterdayStr) currentStreak + 1 else 1
                it[Keys.LAST_ACTIVE_DATE] = todayStr
                if ((it[Keys.TASBEEH_DATE] ?: "") != todayStr) {
                    it[Keys.TASBEEH_TODAY] = 0
                    it[Keys.TASBEEH_DATE] = todayStr
                }
            }
        }
    }
}
