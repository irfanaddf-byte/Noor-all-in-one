package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AppLanguage
import com.example.data.model.CityInfo
import com.example.data.model.ZakatResult
import com.example.data.repository.NamesAndKidsCatalog
import com.example.data.repository.NoorDataCatalog
import com.example.data.repository.UserPreferencesRepository
import com.example.data.repository.UserPrefsSnapshot
import com.example.notifications.NoorNotificationHelper
import com.example.util.DeviceActionHelper
import com.example.util.IslamicDateHelper
import com.example.util.NoorAudioPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NoorUiState(
    val prefs: UserPrefsSnapshot = UserPrefsSnapshot(),
    val activeCity: CityInfo = NoorDataCatalog.cities.first(),
    val nextPrayer: IslamicDateHelper.NextPrayerCountdown = IslamicDateHelper.computeNextPrayerCountdown(NoorDataCatalog.cities.first()),
    val gregorianShort: String = IslamicDateHelper.getGregorianShortDate(),
    val gregorianFull: String = IslamicDateHelper.getGregorianDayAndDate(),
    val hijriDate: String = IslamicDateHelper.getHijriDate(),
    val isGpsDetected: Boolean = false,
    val playingAudioId: String? = null,
    val zakatResult: ZakatResult? = null
) {
    val dailyGoalProgress: Float
        get() {
            val prayerPoints = if (prefs.todayPrayedDate == IslamicDateHelper.getTodayKey()) prefs.todayPrayedSet.size * 10 else 0
            val tasbeehPoints = prefs.tasbeehTodayTotal.coerceAtMost(50)
            val total = prayerPoints + tasbeehPoints
            return (total.toFloat() / prefs.dailyGoalTarget.coerceAtLeast(50)).coerceIn(0f, 1f)
        }

    val dailyGoalPercent: Int get() = (dailyGoalProgress * 100).toInt()
}

class NoorViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserPreferencesRepository(application)
    private val audioPlayer = NoorAudioPlayer(application)

    private val _tick = MutableStateFlow(0L)
    private val _isGpsDetected = MutableStateFlow(false)
    private val _playingAudioId = MutableStateFlow<String?>(null)
    private val _zakatResult = MutableStateFlow<ZakatResult?>(null)

    val uiState: StateFlow<NoorUiState> = combine(
        repo.prefsFlow,
        _tick,
        _isGpsDetected,
        _playingAudioId,
        _zakatResult
    ) { prefs, _, gpsDetected, audioId, zakat ->
        val city = NoorDataCatalog.cities.find { it.name.equals(prefs.selectedCityName, ignoreCase = true) }
            ?: NoorDataCatalog.cities.first()
        NoorUiState(
            prefs = prefs,
            activeCity = city,
            nextPrayer = IslamicDateHelper.computeNextPrayerCountdown(city),
            gregorianShort = IslamicDateHelper.getGregorianShortDate(),
            gregorianFull = IslamicDateHelper.getGregorianDayAndDate(),
            hijriDate = IslamicDateHelper.getHijriDate(),
            isGpsDetected = gpsDetected,
            playingAudioId = audioId,
            zakatResult = zakat
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoorUiState())

    init {
        viewModelScope.launch {
            repo.touchDailyStreak(IslamicDateHelper.getTodayKey(), IslamicDateHelper.getYesterdayKey())
            while (true) {
                delay(1000L)
                _tick.value = System.currentTimeMillis()
            }
        }
        NoorNotificationHelper.ensureChannel(application)
    }

    fun refreshGpsCity() {
        val detected = DeviceActionHelper.detectNearestCity(getApplication())
        if (detected != null) {
            _isGpsDetected.value = true
            viewModelScope.launch {
                repo.setSelectedCity(detected.name)
                repo.setUseGps(true)
            }
        } else {
            _isGpsDetected.value = false
        }
    }

    fun selectCity(cityName: String) {
        _isGpsDetected.value = false
        viewModelScope.launch {
            repo.setSelectedCity(cityName)
            val city = NoorDataCatalog.cities.find { it.name == cityName } ?: NoorDataCatalog.cities.first()
            NoorNotificationHelper.scheduleAzanAlarms(getApplication(), city, uiState.value.prefs.azanAlarms)
        }
    }

    fun saveUserNameAndFinishOnboarding(name: String) {
        viewModelScope.launch {
            repo.saveUserNameAndCompleteOnboarding(name.ifBlank { "Bhaijaan" })
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch { repo.updateUserName(name) }
    }

    fun updateUserPhoto(uri: String) {
        viewModelScope.launch { repo.updateUserPhoto(uri) }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch { repo.setDarkMode(enabled) }
    }

    fun setFontScale(scale: Float) {
        viewModelScope.launch { repo.setFontScale(scale) }
    }

    fun setLanguage(lang: AppLanguage) {
        viewModelScope.launch { repo.setLanguage(lang) }
    }

    fun setVibration(enabled: Boolean) {
        viewModelScope.launch { repo.setVibration(enabled) }
    }

    fun setMorningHadithNotif(enabled: Boolean) {
        viewModelScope.launch {
            repo.setMorningHadithNotif(enabled)
            NoorNotificationHelper.scheduleMorningHadith(getApplication(), enabled)
        }
    }

    fun toggleAzanAlarm(prayerKey: String) {
        viewModelScope.launch {
            repo.toggleAzanAlarm(prayerKey)
            val current = uiState.value.prefs.azanAlarms.toMutableSet()
            if (current.contains(prayerKey)) current.remove(prayerKey) else current.add(prayerKey)
            NoorNotificationHelper.scheduleAzanAlarms(getApplication(), uiState.value.activeCity, current)
        }
    }

    fun sendTestAzanNotification() {
        val city = uiState.value.activeCity
        val next = uiState.value.nextPrayer
        NoorNotificationHelper.showNotification(
            getApplication(),
            999,
            "🕌 ${next.prayerKey} Azan Reminder (${city.name})",
            "${next.prayerKey} ka waqt ${next.prayerTimeDisplay} par hai (10 min pehle alert)."
        )
    }

    fun setLastReadSurah(number: Int) {
        viewModelScope.launch { repo.setLastReadSurah(number) }
    }

    fun toggleSurahBookmark(number: Int) {
        viewModelScope.launch { repo.toggleSurahBookmark(number) }
    }

    fun toggleFavDua(id: Int) {
        viewModelScope.launch { repo.toggleFavouriteDua(id) }
    }

    fun toggleFavHadith(id: Int) {
        viewModelScope.launch { repo.toggleFavouriteHadith(id) }
    }

    fun toggleFavName(number: Int) {
        viewModelScope.launch { repo.toggleFavouriteName(number) }
    }

    fun tapTasbeeh() {
        val prefs = uiState.value.prefs
        if (prefs.vibrationEnabled) {
            DeviceActionHelper.vibrateTap(getApplication())
        }
        viewModelScope.launch {
            repo.updateTasbeehTap(
                newCount = prefs.tasbeehCount + 1,
                newTodayTotal = prefs.tasbeehTodayTotal + 1,
                todayStr = IslamicDateHelper.getTodayKey()
            )
        }
    }

    fun selectZikr(zikrId: String) {
        viewModelScope.launch { repo.setTasbeehZikr(zikrId) }
    }

    fun selectTasbeehTarget(target: Int) {
        viewModelScope.launch { repo.setTasbeehTarget(target) }
    }

    fun saveAndResetTasbeeh() {
        val prefs = uiState.value.prefs
        val zikr = NamesAndKidsCatalog.tasbeehZikrs.find { it.id == prefs.tasbeehZikrId }
            ?: NamesAndKidsCatalog.tasbeehZikrs.first()
        val timeLabel = SimpleDateFormat("dd MMM, hh:mm a", Locale.ENGLISH).format(Date())
        viewModelScope.launch {
            repo.saveAndResetTasbeeh(zikr.title, prefs.tasbeehCount, timeLabel)
        }
    }

    fun toggleTodayPrayer(prayerKey: String) {
        viewModelScope.launch {
            repo.toggleTodayPrayer(prayerKey, IslamicDateHelper.getTodayKey())
        }
    }

    fun adjustQaza(prayerKey: String, delta: Int) {
        viewModelScope.launch { repo.adjustQaza(prayerKey, delta) }
    }

    fun toggleRozaDay(day: Int) {
        val note = "Ramzan Day $day • ${IslamicDateHelper.getGregorianShortDate()}"
        viewModelScope.launch { repo.toggleRozaDay(day, note) }
    }

    fun addNafliRoza() {
        val note = "Nafli Roza • ${IslamicDateHelper.getGregorianShortDate()}"
        viewModelScope.launch { repo.addNafliRoza(note) }
    }

    fun calculateZakat(
        goldTola: Double,
        silverTola: Double,
        bankAmount: Double,
        cashAmount: Double,
        goldRate: Double,
        silverRate: Double
    ) {
        _zakatResult.value = IslamicDateHelper.calculateZakat(
            goldTola = goldTola,
            silverTola = silverTola,
            bankAmount = bankAmount,
            cashAmount = cashAmount,
            goldRatePerTola = goldRate,
            silverRatePerTola = silverRate
        )
    }

    fun togglePlayAudio(audioId: String, spokenText: String, isArabic: Boolean = false) {
        if (_playingAudioId.value == audioId) {
            audioPlayer.stop()
            _playingAudioId.value = null
        } else {
            _playingAudioId.value = audioId
            val locale = if (isArabic) Locale("ar", "SA") else Locale("hi", "IN")
            audioPlayer.speak(spokenText, locale) {
                _playingAudioId.value = null
            }
        }
    }

    fun stopAudio() {
        audioPlayer.stop()
        _playingAudioId.value = null
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
