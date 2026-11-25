package com.example.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.model.CityInfo
import com.example.data.repository.NoorDataCatalog
import java.util.Calendar

class NoorAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Noor Azan Reminder"
        val body = intent.getStringExtra("body") ?: "Namaz ka waqt hone wala hai."
        val id = intent.getIntExtra("notif_id", 1001)
        NoorNotificationHelper.showNotification(context, id, title, body)
    }
}

object NoorNotificationHelper {
    private const val CHANNEL_ID = "noor_azan_hadith_channel"

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (nm.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Noor Azan & Hadith Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Namaz se 10 minute pehle Azan reminder aur Rozana Subah Hadith"
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                    enableVibration(true)
                }
                nm.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(context: Context, id: Int, title: String, body: String) {
        ensureChannel(context)
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentPi = PendingIntent.getActivity(
            context, id, openIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(contentPi)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(id, notif)
    }

    fun scheduleAzanAlarms(context: Context, city: CityInfo, enabledPrayers: Set<String>) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val times = mapOf(
            "Fajr" to city.fajr,
            "Dhuhr" to city.dhuhr,
            "Asr" to city.asr,
            "Maghrib" to city.maghrib,
            "Isha" to city.isha
        )
        times.entries.forEachIndexed { index, (prayer, time24) ->
            val reqCode = 200 + index
            val intent = Intent(context, NoorAlarmReceiver::class.java).apply {
                putExtra("notif_id", reqCode)
                putExtra("title", "🕌 $prayer Azan — 10 Min Reminder")
                putExtra("body", "${city.name}: $prayer ka waqt 10 minute me shuru hoga. Wazu ki tayyari kar lein.")
            }
            val pi = PendingIntent.getBroadcast(
                context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            am.cancel(pi)
            if (enabledPrayers.contains(prayer)) {
                val parts = time24.split(":")
                val h = parts[0].toIntOrNull() ?: 5
                val m = parts[1].toIntOrNull() ?: 0
                val cal = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, h)
                    set(Calendar.MINUTE, m)
                    set(Calendar.SECOND, 0)
                    add(Calendar.MINUTE, -10)
                    if (before(Calendar.getInstance())) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            }
        }
    }

    fun scheduleMorningHadith(context: Context, enabled: Boolean) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val sample = NoorDataCatalog.hadiths.first()
        val intent = Intent(context, NoorAlarmReceiver::class.java).apply {
            putExtra("notif_id", 301)
            putExtra("title", "📖 Aaj Ki Hadith — Noor App")
            putExtra("body", "${sample.textHindi} (${sample.reference})")
        }
        val pi = PendingIntent.getBroadcast(
            context, 301, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        am.cancel(pi)
        if (enabled) {
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 7)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                if (before(Calendar.getInstance())) add(Calendar.DAY_OF_YEAR, 1)
            }
            am.setRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
        }
    }
}
