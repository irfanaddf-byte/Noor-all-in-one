package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.WallpaperItem
import com.example.data.repository.NamesAndKidsCatalog
import com.example.data.repository.NoorDataCatalog
import com.example.data.repository.QuranCatalog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.DeviceActionHelper
import kotlinx.coroutines.delay

@Composable
fun WallpapersScreen() {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    var previewItem by remember { mutableStateOf<WallpaperItem?>(null) }

    previewItem?.let { item ->
        Dialog(onDismissRequest = { previewItem = null }) {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen),
                border = BorderStroke(2.dp, NoorGold)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    WallpaperPreviewBox(item = item, modifier = Modifier.fillMaxWidth().height(320.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(item.title, color = NoorGold, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(item.subtitle, color = NoorWhite.copy(alpha = 0.8f), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { DeviceActionHelper.setAsWallpaper(context, item) },
                            colors = ButtonDefaults.buttonColors(containerColor = NoorGold),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Wallpaper, contentDescription = null, tint = NoorDarkGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Set Wallpaper", color = NoorDarkGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = { DeviceActionHelper.shareWallpaper(context, item) },
                            border = BorderStroke(1.dp, NoorGold),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = NoorGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share", color = NoorGold, fontSize = 12.sp)
                        }
                    }
                    TextButton(onClick = { previewItem = null }) {
                        Text("Close", color = NoorWhite)
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("20 Khoobsurat Islamic Wallpapers", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Tap karein preview, Wallpaper set aur Share karne ke liye", style = MaterialTheme.typography.bodyMedium, color = noor.mutedText)
        }

        items(NoorDataCatalog.wallpapers.chunked(2)) { pair ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                pair.forEach { item ->
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { previewItem = item }
                    ) {
                        Column {
                            WallpaperPreviewBox(item = item, modifier = Modifier.fillMaxWidth().height(175.dp))
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(item.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(item.subtitle, fontSize = 11.sp, color = noor.mutedText, maxLines = 1)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WallpaperPreviewBox(item: WallpaperItem, modifier: Modifier = Modifier) {
    if (item.drawableRes != null) {
        Image(
            painter = painterResource(id = item.drawableRes),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
        )
    } else {
        Box(
            modifier = modifier.background(
                Brush.verticalGradient(listOf(Color(item.primaryHex), Color(item.secondaryHex)))
            ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.calligraphy,
                    fontFamily = AmiriFontFamily,
                    color = Color(item.accentHex),
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "NOOR",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NaatBayanScreen(
    uiState: NoorUiState,
    onToggleAudio: (String, String, Boolean) -> Unit
) {
    val noor = LocalNoorColors.current
    var progressTick by remember { mutableIntStateOf(0) }
    val activeNaatId = uiState.playingAudioId

    LaunchedEffect(activeNaatId) {
        progressTick = 0
        while (activeNaatId != null && activeNaatId.startsWith("naat_")) {
            delay(1000L)
            progressTick++
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Naat & Bayan Audio (10)", color = NoorGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Offline Islamic Naat Shareef aur Bayan sunein", color = NoorWhite.copy(alpha = 0.85f), fontSize = 12.sp)
                }
            }
        }

        items(NoorDataCatalog.naatsAndBayans, key = { it.id }) { item ->
            val audioKey = "naat_${item.id}"
            val isPlaying = activeNaatId == audioKey

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isPlaying) noor.goldSoft else noor.cardSurface),
                border = BorderStroke(1.dp, if (isPlaying) NoorGold else noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(NoorDarkGreen)
                                .clickable { onToggleAudio(audioKey, "${item.title}. ${item.recitationText}", false) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = NoorGold,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("${item.type} • ${item.artist} • ${item.durationSec / 60}:${(item.durationSec % 60).toString().padStart(2, '0')}", fontSize = 12.sp, color = noor.mutedText)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(item.recitationText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                    if (isPlaying) {
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { ((progressTick % 30) / 30f).coerceIn(0.08f, 1f) },
                            color = NoorGold,
                            trackColor = noor.borderColor,
                            modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NearbyMasjidScreen(uiState: NoorUiState) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val city = uiState.activeCity

    val sortedMasjids = remember(city) {
        NoorDataCatalog.masjids.sortedByDescending { it.city.equals(city.name, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nearby Masjids — ${city.name}", color = NoorGold, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text("Thakurdwara aur aas-paas ki Masjidein aur Jamaat ke waqt", color = NoorWhite.copy(alpha = 0.85f), fontSize = 12.sp)
                }
            }
        }

        items(sortedMasjids, key = { it.id }) { m ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.Mosque, contentDescription = null, tint = NoorGold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(m.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("${m.area} • ${m.distanceKm}", fontSize = 12.sp, color = noor.mutedText)
                            }
                        }
                        OutlinedButton(
                            onClick = { DeviceActionHelper.openMapsForMasjid(context, m.name, m.lat, m.lon) },
                            border = BorderStroke(1.dp, NoorGold)
                        ) {
                            Icon(Icons.Default.Directions, contentDescription = null, tint = NoorGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Map", fontSize = 11.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Jamaat: Fajr ${m.fajrJamaat} | Dhuhr ${m.dhuhrJamaat} | Asr ${m.asrJamaat} | Isha ${m.ishaJamaat} | Jummah ${m.jummahJamaat}",
                        fontSize = 11.sp,
                        color = NoorGold,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun RozaTrackerScreen(
    uiState: NoorUiState,
    onToggleRozaDay: (Int) -> Unit,
    onAddNafliRoza: () -> Unit
) {
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Roza Tracker", color = NoorGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Ramzan: ${prefs.rozaDaysCompleted.size}/30 • Nafli Roza: ${prefs.rozaNafliCount}", color = NoorWhite, fontSize = 13.sp)
                    }
                    Button(
                        onClick = onAddNafliRoza,
                        colors = ButtonDefaults.buttonColors(containerColor = NoorGold)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = NoorDarkGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nafli", color = NoorDarkGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text("30 Ramzan Roza Checklist (Tap to Tick)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        items((1..30).chunked(5)) { rowDays ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowDays.forEach { day ->
                    val done = prefs.rozaDaysCompleted.contains(day)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (done) NoorDarkGreen else noor.cardSurface,
                        border = BorderStroke(1.dp, if (done) NoorGold else noor.borderColor),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onToggleRozaDay(day) }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "R$day",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (done) NoorGold else MaterialTheme.colorScheme.onSurface
                            )
                            if (done) {
                                Icon(Icons.Default.Check, contentDescription = "Done", tint = NoorGold, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
        }

        item {
            Text("Roza History", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        if (prefs.rozaHistory.isEmpty()) {
            item {
                Text("Abhi koi Roza log nahi hua. Upar kisi din par tap karein.", color = noor.mutedText, fontSize = 13.sp)
            }
        } else {
            items(prefs.rozaHistory) { h ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                    border = BorderStroke(1.dp, noor.borderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "✅ $h", modifier = Modifier.padding(12.dp), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun KidsScreen(
    uiState: NoorUiState,
    onToggleAudio: (String, String, Boolean) -> Unit
) {
    val noor = LocalNoorColors.current
    var tab by remember { mutableIntStateOf(0) } // 0=6 Kalma, 1=Chhoti Surah, 2=10 Duas, 3=Kahaniyan
    val shortSurahNums = remember { listOf(1, 112, 108, 113, 114) }
    val shortSurahs = remember { QuranCatalog.all114Surahs.filter { it.number in shortSurahNums } }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Kids Islamic Learning Section", color = NoorGold, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text("6 Kalma, Chhoti Surahs, 10 Duas aur Nabiyon ki Kahaniyan", color = NoorWhite.copy(alpha = 0.85f), fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("6 Kalma", "Surahs", "10 Duas", "Kahaniyan").forEachIndexed { idx, label ->
                    val active = tab == idx
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (active) NoorDarkGreen else noor.cardSurface,
                        border = BorderStroke(1.dp, if (active) NoorGold else noor.borderColor),
                        modifier = Modifier.weight(1f).clickable { tab = idx }
                    ) {
                        Text(
                            text = label,
                            color = if (active) NoorGold else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }

        when (tab) {
            0 -> {
                items(NamesAndKidsCatalog.sixKalmas, key = { it.number }) { k ->
                    val audioId = "kalma_${k.number}"
                    val isPlaying = uiState.playingAudioId == audioId
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                        border = BorderStroke(1.dp, noor.borderColor)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(k.title, fontWeight = FontWeight.Bold, color = NoorGold)
                                IconButton(onClick = { onToggleAudio(audioId, "${k.title}. ${k.hindiTranslit}. ${k.hindiMeaning}", false) }) {
                                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp, contentDescription = "Listen", tint = NoorGold)
                                }
                            }
                            Text(k.arabic, fontFamily = AmiriFontFamily, fontSize = 22.sp, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(k.hindiTranslit, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = NoorDarkGreen)
                            Text(k.hindiMeaning, fontSize = 13.sp, color = noor.mutedText)
                        }
                    }
                }
            }
            1 -> {
                items(shortSurahs, key = { it.number }) { s ->
                    val audioId = "kids_surah_${s.number}"
                    val isPlaying = uiState.playingAudioId == audioId
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                        border = BorderStroke(1.dp, noor.borderColor)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("${s.name} (${s.ayahsCount} Ayahs)", fontWeight = FontWeight.Bold, color = NoorGold)
                                IconButton(onClick = {
                                    val txt = s.verses.joinToString(". ") { it.hindiTarjuma }
                                    onToggleAudio(audioId, "${s.name}. $txt", false)
                                }) {
                                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp, contentDescription = "Listen", tint = NoorGold)
                                }
                            }
                            s.verses.forEach { v ->
                                Text(v.arabic, fontFamily = AmiriFontFamily, fontSize = 20.sp, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                                Text(v.hindiTarjuma, fontSize = 13.sp, color = noor.mutedText)
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }
            2 -> {
                items(NamesAndKidsCatalog.kidsDuas, key = { it.id }) { d ->
                    val audioId = "kids_dua_${d.id}"
                    val isPlaying = uiState.playingAudioId == audioId
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                        border = BorderStroke(1.dp, noor.borderColor)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(d.title, fontWeight = FontWeight.Bold, color = NoorGold)
                                IconButton(onClick = { onToggleAudio(audioId, "${d.title}. ${d.transliteration}. ${d.hindiTarjuma}", false) }) {
                                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp, contentDescription = "Listen", tint = NoorGold)
                                }
                            }
                            Text(d.arabic, fontFamily = AmiriFontFamily, fontSize = 21.sp, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                            Text(d.transliteration, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NoorGold)
                            Text(d.hindiTarjuma, fontSize = 13.sp)
                        }
                    }
                }
            }
            else -> {
                items(NamesAndKidsCatalog.prophetStories, key = { it.id }) { st ->
                    val audioId = "story_${st.id}"
                    val isPlaying = uiState.playingAudioId == audioId
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                        border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.6f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(st.title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = NoorGold)
                                    Text(st.subtitle, fontSize = 12.sp, color = noor.mutedText)
                                }
                                IconButton(onClick = { onToggleAudio(audioId, "${st.title}. ${st.storyHindi}. ${st.moralHindi}", false) }) {
                                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp, contentDescription = "Listen Story", tint = NoorGold)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(st.storyHindi, fontSize = 14.sp, lineHeight = 22.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(color = NoorDarkGreen, shape = RoundedCornerShape(12.dp)) {
                                Text(st.moralHindi, color = NoorGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
