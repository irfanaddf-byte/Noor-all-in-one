package com.example.util

import android.Manifest
import android.app.WallpaperManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.location.Location
import android.location.LocationManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.MainActivity
import com.example.R
import com.example.data.model.CityInfo
import com.example.data.model.WallpaperItem
import com.example.data.repository.NoorDataCatalog
import java.io.File
import java.io.FileOutputStream
import java.util.Locale
import kotlin.math.abs

class NoorAudioPlayer(context: Context) {
    private var tts: TextToSpeech? = null
    private var ready = false
    private var pendingText: String? = null
    private var pendingLocale: Locale = Locale("hi", "IN")
    private var onCompleteCallback: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ready = true
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {
                        onCompleteCallback?.invoke()
                    }
                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        onCompleteCallback?.invoke()
                    }
                })
                pendingText?.let { text ->
                    speak(text, pendingLocale, onCompleteCallback)
                    pendingText = null
                }
            }
        }
    }

    fun speak(text: String, locale: Locale = Locale("hi", "IN"), onDone: (() -> Unit)? = null) {
        onCompleteCallback = onDone
        if (!ready) {
            pendingText = text
            pendingLocale = locale
            playGentleTone()
            return
        }
        val res = tts?.setLanguage(locale)
        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.ENGLISH)
        }
        tts?.setSpeechRate(0.92f)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "noor_utterance_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
    }

    fun playGentleTone() {
        try {
            val tg = ToneGenerator(AudioManager.STREAM_MUSIC, 40)
            tg.startTone(ToneGenerator.TONE_PROP_BEEP, 110)
        } catch (_: Throwable) {
        }
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
    }
}

object DeviceActionHelper {

    fun vibrateTap(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vm.defaultVibrator.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(28)
                }
            }
        } catch (_: Throwable) {
        }
    }

    fun copyText(context: Context, label: String, text: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }

    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$text\n\n— Shared via Noor - All In One (By Thakurdwara)")
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun shareOnWhatsApp(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
            setPackage("com.whatsapp")
        }
        try {
            context.startActivity(intent)
        } catch (_: Throwable) {
            shareText(context, text)
        }
    }

    fun openUrl(context: Context, url: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Throwable) {
            Toast.makeText(context, "Unable to open link", Toast.LENGTH_SHORT).show()
        }
    }

    fun openUpiDonate(context: Context, amountRs: Int) {
        val uri = Uri.parse(
            "upi://pay?pa=irfanaddf-1@okaxis&pn=Noor%20Islamic%20App&tn=Support%20Noor%20App%20Thakurdwara&am=$amountRs&cu=INR"
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(Intent.createChooser(intent, "Donate ₹$amountRs via UPI"))
        } catch (_: Throwable) {
            copyText(context, "UPI ID", "irfanaddf-1@okaxis")
            Toast.makeText(context, "UPI app not found. UPI ID copied: irfanaddf-1@okaxis", Toast.LENGTH_LONG).show()
        }
    }

    fun openMapsForMasjid(context: Context, name: String, lat: Double, lon: Double) {
        val uri = Uri.parse("geo:$lat,$lon?q=$lat,$lon(${Uri.encode(name)})")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (_: Throwable) {
            openUrl(context, "https://maps.google.com/?q=$lat,$lon")
        }
    }

    fun pinToHomeScreen(context: Context): Boolean {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val launchIntent = Intent(context, MainActivity::class.java).apply {
                action = Intent.ACTION_MAIN
            }
            val shortcut = ShortcutInfoCompat.Builder(context, "noor_home_shortcut")
                .setShortLabel("Noor")
                .setLongLabel("Noor - All In One")
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(launchIntent)
                .build()
            ShortcutManagerCompat.requestPinShortcut(context, shortcut, null)
            Toast.makeText(context, "Home Screen shortcut request sent!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    fun detectNearestCity(context: Context): CityInfo? {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) return null

        return try {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = lm.getProviders(true)
            var bestLoc: Location? = null
            for (p in providers) {
                val loc = lm.getLastKnownLocation(p) ?: continue
                if (bestLoc == null || loc.accuracy < bestLoc.accuracy) {
                    bestLoc = loc
                }
            }
            bestLoc?.let { loc ->
                NoorDataCatalog.cities.minByOrNull { city ->
                    abs(city.lat - loc.latitude) + abs(city.lon - loc.longitude)
                }
            }
        } catch (_: Throwable) {
            null
        }
    }

    fun renderWallpaperBitmap(context: Context, item: WallpaperItem): Bitmap {
        if (item.drawableRes != null) {
            val decoded = BitmapFactory.decodeResource(context.resources, item.drawableRes)
            if (decoded != null) return decoded
        }
        val w = 1080
        val h = 1920
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val bgPaint = Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, h.toFloat(),
                item.primaryHex.toInt(),
                item.secondaryHex.toInt(),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), bgPaint)

        val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = item.accentHex.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 6f
            alpha = 110
        }
        canvas.drawCircle(w / 2f, h / 2f, 340f, ringPaint)
        canvas.drawCircle(w / 2f, h / 2f, 380f, ringPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = item.accentHex.toInt()
            textSize = 72f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(item.calligraphy, w / 2f, h / 2f, textPaint)

        val subPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.WHITE
            textSize = 36f
            textAlign = Paint.Align.CENTER
            alpha = 190
        }
        canvas.drawText("${item.title} • Noor App", w / 2f, h - 180f, subPaint)
        return bmp
    }

    fun setAsWallpaper(context: Context, item: WallpaperItem) {
        try {
            val bmp = renderWallpaperBitmap(context, item)
            WallpaperManager.getInstance(context).setBitmap(bmp)
            Toast.makeText(context, "Wallpaper applied!", Toast.LENGTH_SHORT).show()
        } catch (_: Throwable) {
            Toast.makeText(context, "Unable to set wallpaper on this device", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareWallpaper(context: Context, item: WallpaperItem) {
        try {
            val bmp = renderWallpaperBitmap(context, item)
            val dir = File(context.cacheDir, "wallpapers").apply { mkdirs() }
            val file = File(dir, "noor_wallpaper_${item.id}.png")
            FileOutputStream(file).use { out ->
                bmp.compress(Bitmap.CompressFormat.PNG, 95, out)
            }
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "${item.title} — Noor Islamic App (By Thakurdwara)")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Wallpaper"))
        } catch (_: Throwable) {
            shareText(context, "${item.title}: ${item.calligraphy}")
        }
    }
}
