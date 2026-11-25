package com.example.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.repository.NoorDataCatalog
import com.example.ui.components.NameEditDialog
import com.example.ui.components.NoorStrings
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.DeviceActionHelper
import com.example.util.IslamicDateHelper
import java.util.Calendar

private data class FeatureGridItem(
    val title: String,
    val subtitle: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    uiState: NoorUiState,
    onNavigate: (String) -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdatePhoto: (String) -> Unit
) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    val city = uiState.activeCity
    val lang = prefs.language

    var showNameDialog by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onUpdatePhoto(uri.toString())
        }
    }

    val decodedAvatar = remember(prefs.userPhotoUri) {
        if (prefs.userPhotoUri.isBlank()) null
        else runCatching {
            context.contentResolver.openInputStream(android.net.Uri.parse(prefs.userPhotoUri))?.use {
                BitmapFactory.decodeStream(it)?.asImageBitmap()
            }
        }.getOrNull()
    }

    val quoteIndex = remember {
        Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % NoorDataCatalog.dailyQuotes.size
    }
    val quote = NoorDataCatalog.dailyQuotes[quoteIndex]

    val features = remember {
        listOf(
            FeatureGridItem("Quran", "114 Surahs", "quran", Icons.Default.Book),
            FeatureGridItem("Namaz Time", "30 Din Table", "namaz", Icons.Default.Schedule),
            FeatureGridItem("Duas", "18+ Masnoon", "duas", Icons.Default.Favorite),
            FeatureGridItem("Qibla", "Live Compass", "qibla", Icons.Default.Explore),
            FeatureGridItem("Tasbeeh", "Zikr Counter", "tasbeeh", Icons.Default.RadioButtonChecked),
            FeatureGridItem("Hadith", "20 Full List", "hadith", Icons.Default.MenuBook),
            FeatureGridItem("Calendar", "Hijri & Eid", "calendar", Icons.Default.CalendarMonth),
            FeatureGridItem("99 Names", "Asma-ul-Husna", "names", Icons.Default.AutoAwesome),
            FeatureGridItem("Zakat", "Calculator", "zakat", Icons.Default.Calculate),
            FeatureGridItem("Kids", "Kalma & Kahani", "kids", Icons.Default.ChildCare),
            FeatureGridItem("Wallpapers", "20 Islamic Art", "wallpapers", Icons.Default.Wallpaper),
            FeatureGridItem("Support", "Donate & Help", "support", Icons.Default.SupportAgent)
        )
    }

    if (showNameDialog) {
        NameEditDialog(
            currentName = prefs.userName,
            onDismiss = { showNameDialog = false },
            onSave = {
                onUpdateName(it)
                showNameDialog = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Greeting + Avatar Photo + Hijri Date + Mausam
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(NoorDarkGreen)
                            .border(2.dp, NoorGold, CircleShape)
                            .clickable {
                                photoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                            .testTag("home_avatar_photo"),
                        contentAlignment = Alignment.Center
                    ) {
                        if (decodedAvatar != null) {
                            Image(
                                bitmap = decodedAvatar,
                                contentDescription = "User Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Tap to choose photo",
                                tint = NoorGold,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${NoorStrings.greetingPrefix(lang)} ${prefs.userName.ifBlank { "Bhaijaan" }}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            IconButton(
                                onClick = { showNameDialog = true },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Name",
                                    tint = NoorGold,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = "${uiState.gregorianFull} • ${uiState.hijriDate}",
                            style = MaterialTheme.typography.labelSmall,
                            color = noor.mutedText
                        )
                        Text(
                            text = "Mausam ${city.name}: ${city.weatherTemp} (${city.weatherCond})",
                            style = MaterialTheme.typography.labelSmall,
                            color = NoorGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // 2. Bada Dark Green Next Prayer + Live Countdown Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("namaz") }
                    .testTag("home_next_prayer_card")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(listOf(NoorDeepEmerald, NoorDarkGreen))
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_mosque_hero),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.22f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(172.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = NoorGold.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.6f))
                            ) {
                                Text(
                                    text = NoorStrings.nextPrayerLabel(lang),
                                    color = NoorGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "Hanafi • Karachi",
                                color = NoorWhite.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column {
                                Text(
                                    text = uiState.nextPrayer.prayerKey,
                                    color = NoorWhite,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = uiState.nextPrayer.prayerTimeDisplay,
                                    color = NoorGold,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (uiState.nextPrayer.isTomorrow) "Kal Fajr me baki" else "Waqt baki hai",
                                    color = NoorWhite.copy(alpha = 0.78f),
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = uiState.nextPrayer.remainingFormatted,
                                    color = NoorGold,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "☀️ Sunrise: ${IslamicDateHelper.format12Hour(city.sunrise)}",
                                color = NoorWhite.copy(alpha = 0.9f),
                                fontSize = 11.sp
                            )
                            Text(
                                text = "🌙 Sehri: ${IslamicDateHelper.format12Hour(city.sehri)}",
                                color = NoorGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "🌅 Iftar: ${IslamicDateHelper.format12Hour(city.iftar)}",
                                color = NoorGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // 3. 5 Chhote Prayer Cards
        item {
            val prayers = IslamicDateHelper.getCityPrayerEntries(city)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                prayers.forEach { p ->
                    val isNext = p.key == uiState.nextPrayer.prayerKey
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isNext) NoorDarkGreen else noor.cardSurface
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isNext) NoorGold else noor.borderColor
                        ),
                        modifier = Modifier
                            .width(96.dp)
                            .clickable { onNavigate("namaz") }
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = when (p.key) {
                                    "Fajr" -> Icons.Default.WbTwilight
                                    "Dhuhr", "Asr" -> Icons.Default.WbSunny
                                    else -> Icons.Default.NightsStay
                                },
                                contentDescription = p.key,
                                tint = if (isNext) NoorGold else NoorDarkGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = p.key,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (isNext) NoorWhite else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = p.timeDisplay,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isNext) NoorGold else noor.mutedText
                            )
                        }
                    }
                }
            }
        }

        // 4. Daily Goal + Progress Bar + Streak
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NoorStrings.dailyGoalTitle(lang),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = NoorGold.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = NoorGold,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${prefs.streakDays} Din Streak",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (noor.isDark) NoorGold else NoorDarkGreen
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { uiState.dailyGoalProgress },
                        color = NoorGold,
                        trackColor = noor.subtleSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val todayCount = if (prefs.todayPrayedDate == IslamicDateHelper.getTodayKey()) prefs.todayPrayedSet.size else 0
                        Text(
                            text = "Namaz: $todayCount/5 • Tasbeeh aaj: ${prefs.tasbeehTodayTotal}",
                            style = MaterialTheme.typography.labelSmall,
                            color = noor.mutedText
                        )
                        Text(
                            text = "${uiState.dailyGoalPercent}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = NoorGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 5. Aaj Ka Quote + Share Button
        item {
            val quoteText = when (lang) {
                AppLanguage.HINDI -> quote.textHindi
                AppLanguage.URDU -> quote.textUrdu
                AppLanguage.ENGLISH -> quote.textEnglish
            }
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.goldSoft),
                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FormatQuote, contentDescription = null, tint = NoorGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = NoorStrings.todaysQuoteTitle(lang),
                                fontWeight = FontWeight.Bold,
                                color = if (noor.isDark) NoorGold else NoorDarkGreen
                            )
                        }
                        Row {
                            IconButton(
                                onClick = { DeviceActionHelper.copyText(context, "Quote", "$quoteText (${quote.source})") },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy Quote", tint = NoorGold, modifier = Modifier.size(18.dp))
                            }
                            IconButton(
                                onClick = { DeviceActionHelper.shareText(context, "$quoteText\n— ${quote.source}") },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("home_share_quote_button")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share Quote", tint = NoorGold, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = quoteText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "— ${quote.source}",
                        style = MaterialTheme.typography.labelSmall,
                        color = NoorGold,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 6. Quick Extra Strip (Naat, Nearby Masjid, Roza Tracker)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickExtraPill("Naat / Bayan", Icons.Default.MusicNote, Modifier.weight(1f)) { onNavigate("naat") }
                QuickExtraPill("Nearby Masjid", Icons.Default.Mosque, Modifier.weight(1f)) { onNavigate("masjid") }
                QuickExtraPill("Roza Tracker", Icons.Default.NightsStay, Modifier.weight(1f)) { onNavigate("roza") }
            }
        }

        // 7. 12-Item Features Grid
        item {
            Text(
                text = NoorStrings.featuresTitle(lang),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(features.chunked(3).size) { rowIndex ->
            val rowItems = features.chunked(3)[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                        border = BorderStroke(1.dp, noor.borderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigate(item.route) }
                            .testTag("feature_tile_${item.route}")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp, horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(NoorDarkGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = NoorGold,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.subtitle,
                                fontSize = 10.sp,
                                color = noor.mutedText,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "بِسْمِ اللَّهِ • Noor - All In One - By Thakurdwara",
                fontFamily = AmiriFontFamily,
                fontSize = 13.sp,
                color = NoorGold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun QuickExtraPill(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = NoorDarkGreen,
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("quick_pill_${label.lowercase().replace(" ", "_")}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = label, tint = NoorGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                color = NoorWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
