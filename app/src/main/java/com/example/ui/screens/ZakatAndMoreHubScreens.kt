package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.DeviceActionHelper

@Composable
fun ZakatScreen(
    uiState: NoorUiState,
    onCalculate: (Double, Double, Double, Double, Double, Double) -> Unit
) {
    val noor = LocalNoorColors.current
    var goldTola by remember { mutableStateOf("") }
    var silverTola by remember { mutableStateOf("") }
    var bankPaisa by remember { mutableStateOf("") }
    var cashPaisa by remember { mutableStateOf("") }
    var goldRate by remember { mutableStateOf("88000") }
    var silverRate by remember { mutableStateOf("1050") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Zakat Calculator (2.5%)", color = NoorGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "सोने का निसाब: 7.5 तोला (87.48g) | चाँदी का निसाब: 52.5 तोला (612.36g)",
                    color = NoorWhite,
                    fontSize = 12.sp
                )
            }
        }

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
            border = BorderStroke(1.dp, noor.borderColor)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = goldTola,
                    onValueChange = { goldTola = it },
                    label = { Text("Sona (Gold in Tola)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("zakat_gold_input")
                )
                OutlinedTextField(
                    value = silverTola,
                    onValueChange = { silverTola = it },
                    label = { Text("Chandi (Silver in Tola)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("zakat_silver_input")
                )
                OutlinedTextField(
                    value = bankPaisa,
                    onValueChange = { bankPaisa = it },
                    label = { Text("Bank Paisa (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("zakat_bank_input")
                )
                OutlinedTextField(
                    value = cashPaisa,
                    onValueChange = { cashPaisa = it },
                    label = { Text("Cash (Haath me Paisa ₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("zakat_cash_input")
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = goldRate,
                        onValueChange = { goldRate = it },
                        label = { Text("Sona Rate/Tola ₹") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = silverRate,
                        onValueChange = { silverRate = it },
                        label = { Text("Chandi Rate/Tola ₹") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        onCalculate(
                            goldTola.toDoubleOrNull() ?: 0.0,
                            silverTola.toDoubleOrNull() ?: 0.0,
                            bankPaisa.toDoubleOrNull() ?: 0.0,
                            cashPaisa.toDoubleOrNull() ?: 0.0,
                            goldRate.toDoubleOrNull() ?: 88000.0,
                            silverRate.toDoubleOrNull() ?: 1050.0
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("zakat_calculate_button")
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null, tint = NoorGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calculate Zakat", color = NoorWhite, fontWeight = FontWeight.Bold)
                }
            }
        }

        uiState.zakatResult?.let { res ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (res.isFarz) NoorDarkGreen else noor.goldSoft
                ),
                border = BorderStroke(1.5.dp, NoorGold)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (res.isFarz) "✅ Zakat Farz Hai!" else "ℹ️ Abhi Zakat Farz Nahi Hai",
                        color = if (res.isFarz) NoorGold else NoorDarkGreen,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Kitni Banti Hai: ₹ ${res.zakatAmount.toLong()}",
                        color = if (res.isFarz) NoorWhite else NoorDarkGreen,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Kul Sampatti: ₹ ${res.totalWealth.toLong()} | Nisab: ₹ ${res.nisabThreshold.toLong()}",
                        color = if (res.isFarz) NoorWhite.copy(alpha = 0.85f) else noor.mutedText,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = res.explanationHindi,
                        color = if (res.isFarz) NoorWhite else MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MoreScreen(
    uiState: NoorUiState,
    onNavigate: (String) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    onSetFontScale: (Float) -> Unit,
    onSetLanguage: (AppLanguage) -> Unit,
    onToggleVibration: (Boolean) -> Unit,
    onToggleMorningNotif: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    var showRateDialog by remember { mutableStateOf(false) }

    if (showRateDialog) {
        var stars by remember { mutableStateOf(5) }
        AlertDialog(
            onDismissRequest = { showRateDialog = false },
            title = { Text("Rate Noor App ⭐", fontWeight = FontWeight.Bold) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Aapko Noor - All In One - By Thakurdwara kaisi lagi?")
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        (1..5).forEach { s ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star $s",
                                tint = if (s <= stars) NoorGold else noor.mutedText,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { stars = s }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showRateDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen)
                ) {
                    Text("JazakAllah Khair!", color = NoorWhite)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRateDialog = false }) { Text("Close") }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Extra Modules Section
        item {
            Text("Extra Islamic Features", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column {
                    MoreMenuItem("Islamic Wallpapers (20)", "Download, Share & Set Wallpaper", Icons.Default.Wallpaper) { onNavigate("wallpapers") }
                    HorizontalDivider(color = noor.borderColor)
                    MoreMenuItem("Naat / Bayan Audio (10)", "Play & Pause Islamic Audio", Icons.Default.MusicNote) { onNavigate("naat") }
                    HorizontalDivider(color = noor.borderColor)
                    MoreMenuItem("Nearby Masjid (GPS)", "Thakurdwara aur Paas ki Masjidein", Icons.Default.Mosque) { onNavigate("masjid") }
                    HorizontalDivider(color = noor.borderColor)
                    MoreMenuItem("Roza Tracker", "30 Ramzan & Nafli Roza History", Icons.Default.NightsStay) { onNavigate("roza") }
                    HorizontalDivider(color = noor.borderColor)
                    MoreMenuItem("Kids Section", "6 Kalma, Chhoti Surah, Duas & Kahaniyan", Icons.Default.ChildCare) { onNavigate("kids") }
                    HorizontalDivider(color = noor.borderColor)
                    MoreMenuItem("Support, Donate & Instagram", "Help, FAQ, UPI Donate & Shortcut", Icons.Default.SupportAgent) { onNavigate("support") }
                }
            }
        }

        // Settings Card
        item {
            Text("App Settings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Dark Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DarkMode, contentDescription = null, tint = NoorGold)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Dark Mode", fontWeight = FontWeight.SemiBold)
                        }
                        Switch(
                            checked = prefs.darkMode,
                            onCheckedChange = onToggleDarkMode,
                            colors = SwitchDefaults.colors(checkedThumbColor = NoorGold, checkedTrackColor = NoorDarkGreen)
                        )
                    }

                    // Vibration
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Vibration, contentDescription = null, tint = NoorGold)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Tasbeeh Vibration", fontWeight = FontWeight.SemiBold)
                        }
                        Switch(
                            checked = prefs.vibrationEnabled,
                            onCheckedChange = onToggleVibration,
                            colors = SwitchDefaults.colors(checkedThumbColor = NoorGold, checkedTrackColor = NoorDarkGreen)
                        )
                    }

                    // Morning Hadith Notification
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = NoorGold)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Morning Hadith Alert", fontWeight = FontWeight.SemiBold)
                        }
                        Switch(
                            checked = prefs.morningHadithNotif,
                            onCheckedChange = onToggleMorningNotif,
                            colors = SwitchDefaults.colors(checkedThumbColor = NoorGold, checkedTrackColor = NoorDarkGreen)
                        )
                    }

                    HorizontalDivider(color = noor.borderColor)

                    // Font Size
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FormatSize, contentDescription = null, tint = NoorGold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Font Size (${(prefs.fontScale * 100).toInt()}%)", fontWeight = FontWeight.SemiBold)
                    }
                    Slider(
                        value = prefs.fontScale,
                        onValueChange = onSetFontScale,
                        valueRange = 0.85f..1.35f,
                        colors = SliderDefaults.colors(thumbColor = NoorGold, activeTrackColor = NoorDarkGreen)
                    )

                    HorizontalDivider(color = noor.borderColor)

                    // Language Hindi / Urdu / English
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = NoorGold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Language (Hindi / Urdu / English)", fontWeight = FontWeight.SemiBold)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppLanguage.entries.forEach { lang ->
                            FilterChip(
                                selected = prefs.language == lang,
                                onClick = { onSetLanguage(lang) },
                                label = { Text("${lang.label} (${lang.nativeLabel})", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NoorDarkGreen,
                                    selectedLabelColor = NoorGold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Rate Us + Invite Friend via WhatsApp
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { showRateDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = NoorGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Rate Us", color = NoorWhite, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        DeviceActionHelper.shareOnWhatsApp(
                            context,
                            "Assalamualaikum! Main *Noor - All In One (By Thakurdwara)* Islamic App use kar raha hoon — Namaz Time, 114 Surah Quran, Duas, Qibla, Tasbeeh, Zakat aur Kids Section sab kuch FREE & Offline hai! Aaj hi try karein."
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorGold),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = NoorDarkGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Invite WhatsApp", color = NoorDarkGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun MoreMenuItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val noor = LocalNoorColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = NoorGold, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(subtitle, fontSize = 12.sp, color = noor.mutedText)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = noor.mutedText)
    }
}
