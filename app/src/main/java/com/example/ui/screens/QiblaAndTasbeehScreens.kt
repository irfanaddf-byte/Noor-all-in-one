package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.repository.NamesAndKidsCatalog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.IslamicDateHelper
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun QiblaScreen(
    uiState: NoorUiState,
    onDetectGps: () -> Unit
) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val city = uiState.activeCity

    val qiblaBearing = remember(city) {
        IslamicDateHelper.calculateQiblaBearing(city.lat, city.lon)
    }
    val qiblaDir = remember(qiblaBearing) { IslamicDateHelper.directionLabel(qiblaBearing) }
    val distanceKm = remember(city) { IslamicDateHelper.calculateDistanceToKaabaKm(city.lat, city.lon) }

    var sensorAzimuth by remember { mutableFloatStateOf(0f) }
    var manualOffset by remember { mutableFloatStateOf(0f) }
    var hasSensor by remember { mutableStateOf(false) }

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onDetectGps()
    }

    DisposableEffect(context) {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotSensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        val listener = object : SensorEventListener {
            val rotMatrix = FloatArray(9)
            val orientation = FloatArray(3)
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    hasSensor = true
                    SensorManager.getRotationMatrixFromVector(rotMatrix, event.values)
                    SensorManager.getOrientation(rotMatrix, orientation)
                    val deg = ((Math.toDegrees(orientation[0].toDouble()) + 360.0) % 360.0).toFloat()
                    sensorAzimuth = deg
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        if (rotSensor != null) {
            sm.registerListener(listener, rotSensor, SensorManager.SENSOR_DELAY_UI)
        }
        onDispose { sm.unregisterListener(listener) }
    }

    val effectiveAzimuth = (sensorAzimuth + manualOffset) % 360f
    val dialRotation by animateFloatAsState(
        targetValue = -effectiveAzimuth,
        animationSpec = spring(),
        label = "qiblaDial"
    )
    val qiblaNeedleRotation = (qiblaBearing.toFloat() - effectiveAzimuth)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Qibla Compass", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("${city.name} • $distanceKm km from Makkah", style = MaterialTheme.typography.labelSmall, color = noor.mutedText)
            }
            OutlinedButton(
                onClick = { locationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
                border = BorderStroke(1.dp, NoorGold)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "GPS", tint = NoorGold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("GPS", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Big Circular Compass with Kaaba in center
        Box(
            modifier = Modifier.size(280.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(dialRotation)
            ) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val radius = size.minDimension / 2f - 12.dp.toPx()

                drawCircle(
                    brush = Brush.radialGradient(listOf(NoorDarkGreen, NoorDeepEmerald)),
                    radius = radius
                )
                drawCircle(
                    color = NoorGold,
                    radius = radius,
                    style = Stroke(width = 4.dp.toPx())
                )
                for (angle in 0 until 360 step 15) {
                    val rad = Math.toRadians(angle.toDouble() - 90.0)
                    val inner = if (angle % 90 == 0) radius - 22.dp.toPx() else radius - 12.dp.toPx()
                    drawLine(
                        color = if (angle == 0) NoorGold else Color.White.copy(alpha = 0.5f),
                        start = Offset(cx + inner * cos(rad).toFloat(), cy + inner * sin(rad).toFloat()),
                        end = Offset(cx + radius * cos(rad).toFloat(), cy + radius * sin(rad).toFloat()),
                        strokeWidth = if (angle % 90 == 0) 3.dp.toPx() else 1.5.dp.toPx()
                    )
                }
            }

            // Qibla Needle pointing toward Kaaba
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(qiblaNeedleRotation)
            ) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                drawLine(
                    color = NoorGold,
                    start = Offset(cx, cy),
                    end = Offset(cx, 26.dp.toPx()),
                    strokeWidth = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawCircle(
                    color = NoorGold,
                    radius = 8.dp.toPx(),
                    center = Offset(cx, 26.dp.toPx())
                )
            }

            // Kaaba Center Medallion
            Image(
                painter = painterResource(id = R.drawable.img_kaaba_art),
                contentDescription = "Holy Kaaba",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .border(2.5.dp, NoorGold, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Green Patti: "Kaaba is XX° Direction"
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = NoorDarkGreen,
            border = BorderStroke(1.dp, NoorGold),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kaaba is ${qiblaBearing.roundToInt()}° $qiblaDir",
                    color = NoorGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (hasSensor) "Live Compass Sensor Active • Phone ko flat rakhein"
                    else "City Coordinates Active • Neeche slider se angle test karein",
                    color = NoorWhite.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
            border = BorderStroke(1.dp, noor.borderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Compass Calibration / Manual Rotate (${manualOffset.roundToInt()}°)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Slider(
                    value = manualOffset,
                    onValueChange = { manualOffset = it },
                    valueRange = 0f..360f,
                    colors = SliderDefaults.colors(thumbColor = NoorGold, activeTrackColor = NoorDarkGreen)
                )
            }
        }
    }
}

@Composable
fun TasbeehScreen(
    uiState: NoorUiState,
    onTap: () -> Unit,
    onSelectZikr: (String) -> Unit,
    onSelectTarget: (Int) -> Unit,
    onSaveAndReset: () -> Unit,
    onToggleVibration: (Boolean) -> Unit
) {
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    val activeZikr = remember(prefs.tasbeehZikrId) {
        NamesAndKidsCatalog.tasbeehZikrs.find { it.id == prefs.tasbeehZikrId }
            ?: NamesAndKidsCatalog.tasbeehZikrs.first()
    }
    val progress = (prefs.tasbeehCount.toFloat() / prefs.tasbeehTarget.coerceAtLeast(1)).coerceIn(0f, 1f)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Zikr Selector
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                NamesAndKidsCatalog.tasbeehZikrs.forEach { z ->
                    FilterChip(
                        selected = z.id == activeZikr.id,
                        onClick = { onSelectZikr(z.id) },
                        label = { Text(z.title, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NoorDarkGreen,
                            selectedLabelColor = NoorGold
                        )
                    )
                }
            }
        }

        // Active Zikr Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = activeZikr.arabic,
                        fontFamily = AmiriFontFamily,
                        fontSize = 30.sp,
                        color = NoorGold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = activeZikr.title,
                        color = NoorWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = activeZikr.meaning,
                        color = NoorWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Big Golden Circular Tap Button
        item {
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    color = NoorGold,
                    trackColor = noor.borderColor,
                    strokeWidth = 10.dp,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    shape = CircleShape,
                    color = NoorGold,
                    shadowElevation = 10.dp,
                    border = BorderStroke(4.dp, NoorDarkGreen),
                    modifier = Modifier
                        .size(188.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onTap)
                        .testTag("tasbeeh_tap_button")
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${prefs.tasbeehCount}",
                            fontSize = 46.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NoorDarkGreen
                        )
                        Text(
                            text = "Taps • Goal ${prefs.tasbeehTarget}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NoorDarkGreen.copy(alpha = 0.85f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "TAP HERE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NoorDarkGreen.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        // Target Selector (100/500/1000/5000) + Today Total
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(33, 100, 500, 1000, 5000).forEach { target ->
                    val selected = prefs.tasbeehTarget == target
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (selected) NoorDarkGreen else noor.cardSurface,
                        border = BorderStroke(1.dp, if (selected) NoorGold else noor.borderColor),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onSelectTarget(target) }
                    ) {
                        Text(
                            text = target.toString(),
                            color = if (selected) NoorGold else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Controls: Vibration, Aaj Ka Total, Save & Reset
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Aaj ka Total Zikr", fontSize = 12.sp, color = noor.mutedText)
                            Text("${prefs.tasbeehTodayTotal} Taps", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = NoorGold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Vibration, contentDescription = null, tint = NoorGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Switch(
                                checked = prefs.vibrationEnabled,
                                onCheckedChange = onToggleVibration,
                                colors = SwitchDefaults.colors(checkedThumbColor = NoorGold, checkedTrackColor = NoorDarkGreen)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onSaveAndReset,
                        colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("tasbeeh_save_reset_button")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, tint = NoorGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save to History & Reset", color = NoorWhite, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // History Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = NoorGold, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tasbeeh History", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        if (prefs.tasbeehHistory.isEmpty()) {
            item {
                Text(
                    text = "Abhi koi history save nahi hui. Zikr karke 'Save to History & Reset' dabayein.",
                    color = noor.mutedText,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
        } else {
            items(prefs.tasbeehHistory) { entry ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                    border = BorderStroke(1.dp, noor.borderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(entry.zikrTitle, fontWeight = FontWeight.Bold)
                            Text(entry.timestampLabel, fontSize = 11.sp, color = noor.mutedText)
                        }
                        Text("${entry.count} Taps", fontWeight = FontWeight.ExtraBold, color = NoorGold)
                    }
                }
            }
        }
    }
}
