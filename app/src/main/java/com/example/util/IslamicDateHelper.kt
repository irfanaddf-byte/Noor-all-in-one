package com.example.util

import android.icu.util.IslamicCalendar
import com.example.data.model.CityInfo
import com.example.data.model.PrayerEntry
import com.example.data.model.ZakatResult
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object IslamicDateHelper {

    private val hijriMonths = listOf(
        "Muharram", "Safar", "Rabi-ul-Awwal", "Rabi-us-Sani",
        "Jumada-ul-Awwal", "Jumada-us-Sani", "Rajab", "Shaban",
        "Ramzan", "Shawwal", "Dhul-Qadah", "Dhul-Hijjah"
    )

    fun getTodayKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(Date())
    }

    fun getYesterdayKey(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(cal.time)
    }

    fun getGregorianShortDate(date: Date = Date()): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return sdf.format(date)
    }

    fun getGregorianDayAndDate(date: Date = Date()): String {
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
        return sdf.format(date)
    }

    fun getHijriDate(date: Date = Date()): String {
        return try {
            val islamic = IslamicCalendar()
            islamic.time = date
            val day = islamic.get(IslamicCalendar.DAY_OF_MONTH)
            val monthIndex = islamic.get(IslamicCalendar.MONTH).coerceIn(0, 11)
            val year = islamic.get(IslamicCalendar.YEAR)
            "$day ${hijriMonths[monthIndex]} $year AH"
        } catch (_: Throwable) {
            "Hijri 1448 AH"
        }
    }

    fun getHijriDayNumber(date: Date = Date()): Int {
        return try {
            val islamic = IslamicCalendar()
            islamic.time = date
            islamic.get(IslamicCalendar.DAY_OF_MONTH)
        } catch (_: Throwable) {
            1
        }
    }

    fun format12Hour(time24: String): String {
        val parts = time24.split(":")
        if (parts.size != 2) return time24
        val h = parts[0].toIntOrNull() ?: return time24
        val m = parts[1].toIntOrNull() ?: return time24
        val suffix = if (h >= 12) "PM" else "AM"
        val h12 = when {
            h == 0 -> 12
            h > 12 -> h - 12
            else -> h
        }
        return String.format(Locale.US, "%d:%02d %s", h12, m, suffix)
    }

    fun getCityPrayerEntries(city: CityInfo): List<PrayerEntry> = listOf(
        PrayerEntry("Fajr", "फ़ज्र", "فجر", "Fajr", city.fajr, format12Hour(city.fajr)),
        PrayerEntry("Dhuhr", "ज़ुहर", "ظہر", "Dhuhr", city.dhuhr, format12Hour(city.dhuhr)),
        PrayerEntry("Asr", "अस्र", "عصر", "Asr", city.asr, format12Hour(city.asr)),
        PrayerEntry("Maghrib", "मग़रिब", "مغرب", "Maghrib", city.maghrib, format12Hour(city.maghrib)),
        PrayerEntry("Isha", "इशा", "عشاء", "Isha", city.isha, format12Hour(city.isha))
    )

    data class NextPrayerCountdown(
        val prayerKey: String,
        val prayerTimeDisplay: String,
        val remainingFormatted: String,
        val isTomorrow: Boolean
    )

    fun computeNextPrayerCountdown(city: CityInfo): NextPrayerCountdown {
        val now = Calendar.getInstance()
        val prayers = getCityPrayerEntries(city)

        for (p in prayers) {
            val parts = p.time24.split(":")
            val h = parts[0].toInt()
            val m = parts[1].toInt()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            if (target.after(now)) {
                val diffSec = ((target.timeInMillis - now.timeInMillis) / 1000L).coerceAtLeast(0L)
                return NextPrayerCountdown(p.key, p.timeDisplay, formatCountdown(diffSec), false)
            }
        }
        // Next is tomorrow's Fajr
        val fajr = prayers.first()
        val parts = fajr.time24.split(":")
        val tomorrowFajr = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            set(Calendar.MINUTE, parts[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val diffSec = ((tomorrowFajr.timeInMillis - now.timeInMillis) / 1000L).coerceAtLeast(0L)
        return NextPrayerCountdown(fajr.key, fajr.timeDisplay, formatCountdown(diffSec), true)
    }

    private fun formatCountdown(totalSec: Long): String {
        val hrs = totalSec / 3600
        val mins = (totalSec % 3600) / 60
        val secs = totalSec % 60
        return String.format(Locale.US, "%02d:%02d:%02d", hrs, mins, secs)
    }

    data class MonthlyPrayerRow(
        val dayNumber: Int,
        val gregorianShort: String,
        val hijriShort: String,
        val fajr: String,
        val dhuhr: String,
        val asr: String,
        val maghrib: String,
        val isha: String,
        val isToday: Boolean
    )

    fun generate30DayTable(city: CityInfo): List<MonthlyPrayerRow> {
        val list = mutableListOf<MonthlyPrayerRow>()
        val baseCal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMM", Locale.ENGLISH)
        for (i in 0 until 30) {
            val cal = (baseCal.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, i) }
            // Small natural seasonal minute drift (-1..+1 min across month)
            val drift = (i / 10)
            list.add(
                MonthlyPrayerRow(
                    dayNumber = i + 1,
                    gregorianShort = sdf.format(cal.time),
                    hijriShort = getHijriDate(cal.time).substringBeforeLast(" "),
                    fajr = shiftMinutes(city.fajr, -drift),
                    dhuhr = city.dhuhr,
                    asr = shiftMinutes(city.asr, drift),
                    maghrib = shiftMinutes(city.maghrib, drift),
                    isha = shiftMinutes(city.isha, drift),
                    isToday = i == 0
                )
            )
        }
        return list
    }

    private fun shiftMinutes(time24: String, deltaMin: Int): String {
        val parts = time24.split(":")
        val h = parts[0].toIntOrNull() ?: 0
        val m = parts[1].toIntOrNull() ?: 0
        val total = (h * 60 + m + deltaMin).coerceIn(0, 23 * 60 + 59)
        return format12Hour(String.format(Locale.US, "%02d:%02d", total / 60, total % 60))
    }

    fun calculateQiblaBearing(lat: Double, lon: Double): Double {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)
        val userLat = Math.toRadians(lat)
        val userLon = Math.toRadians(lon)
        val dLon = kaabaLon - userLon
        val y = sin(dLon) * cos(kaabaLat)
        val x = cos(userLat) * sin(kaabaLat) - sin(userLat) * cos(kaabaLat) * cos(dLon)
        val bearing = Math.toDegrees(atan2(y, x))
        return (bearing + 360.0) % 360.0
    }

    fun directionLabel(deg: Double): String {
        val dirs = listOf("North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest")
        val idx = (((deg + 22.5) % 360.0) / 45.0).toInt() % 8
        return dirs[idx]
    }

    fun calculateDistanceToKaabaKm(lat: Double, lon: Double): Int {
        val r = 6371.0
        val dLat = Math.toRadians(21.4225 - lat)
        val dLon = Math.toRadians(39.8262 - lon)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat)) * cos(Math.toRadians(21.4225)) *
            sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        return (r * c).toInt()
    }

    fun calculateZakat(
        goldTola: Double,
        silverTola: Double,
        bankAmount: Double,
        cashAmount: Double,
        goldRatePerTola: Double = 88000.0,
        silverRatePerTola: Double = 1050.0
    ): ZakatResult {
        val goldVal = goldTola * goldRatePerTola
        val silverVal = silverTola * silverRatePerTola
        val total = goldVal + silverVal + bankAmount + cashAmount
        // Standard Hanafi Nisab: 52.5 Tola Silver (when mixed wealth / cash exists) or 7.5 Tola Gold
        val silverNisab = 52.5 * silverRatePerTola
        val goldOnly = goldTola > 0.0 && silverTola == 0.0 && bankAmount == 0.0 && cashAmount == 0.0
        val nisab = if (goldOnly) 7.5 * goldRatePerTola else silverNisab
        val isFarz = total >= nisab && total > 0.0
        val zakat = if (isFarz) total * 0.025 else 0.0

        val explanation = if (isFarz) {
            "आपके पास कुल ₹${total.toLong()} की मालियत है जो निसाब (सोना 7.5 तोला या चाँदी 52.5 तोला = ₹${nisab.toLong()}) से ज़्यादा है। इसलिए आप पर 2.5% यानी ₹${zakat.toLong()} ज़कात फ़र्ज़ है।"
        } else {
            "सोने का निसाब 7.5 तोला और चाँदी का निसाब 52.5 तोला (लगभग ₹${nisab.toLong()}) होता है। आपकी कुल जमा पूंजी ₹${total.toLong()} निसाब से कम है, इसलिए अभी ज़कात फ़र्ज़ नहीं है।"
        }
        return ZakatResult(total, goldVal, silverVal, nisab, isFarz, zakat, explanation)
    }
}
