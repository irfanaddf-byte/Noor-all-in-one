package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.data.repository.QuranCatalog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.DeviceActionHelper

@Composable
fun QuranListScreen(
    uiState: NoorUiState,
    onOpenSurah: (Int) -> Unit,
    onToggleBookmark: (Int) -> Unit
) {
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }

    val lastReadSurah = remember(prefs.lastReadSurah) {
        QuranCatalog.all114Surahs.find { it.number == prefs.lastReadSurah } ?: QuranCatalog.all114Surahs.first()
    }

    val filtered = remember(query, filter, prefs.bookmarkedSurahs) {
        QuranCatalog.all114Surahs.filter { surah ->
            val matchesQuery = query.isBlank() ||
                surah.name.contains(query, ignoreCase = true) ||
                surah.number.toString() == query.trim() ||
                surah.revelationType.contains(query, ignoreCase = true)
            val matchesFilter = when (filter) {
                "Makki" -> surah.revelationType == "Makki"
                "Madani" -> surah.revelationType == "Madani"
                "Bookmarks" -> prefs.bookmarkedSurahs.contains(surah.number)
                else -> true
            }
            matchesQuery && matchesFilter
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Header Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(NoorDeepEmerald, NoorDarkGreen)))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_quran_art),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.25f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(126.dp)
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Al-Quran Al-Kareem",
                            fontFamily = AmiriFontFamily,
                            color = NoorGold,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Quran - 114 Surahs (Offline Ready)",
                            color = NoorWhite,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = NoorGold.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.6f)),
                            modifier = Modifier
                                .clickable { onOpenSurah(lastReadSurah.number) }
                                .testTag("quran_last_read_chip")
                        ) {
                            Text(
                                text = "📖 Last Read: ${lastReadSurah.number}. ${lastReadSurah.name} • Resume",
                                color = NoorGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search Surah...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quran_search_input")
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Makki", "Madani", "Bookmarks").forEach { tab ->
                    FilterChip(
                        selected = filter == tab,
                        onClick = { filter = tab },
                        label = { Text(tab) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NoorDarkGreen,
                            selectedLabelColor = NoorGold
                        )
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Koi Surah nahi mili. Filter ya Search badal kar dekhein.",
                        modifier = Modifier.padding(20.dp),
                        color = noor.mutedText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(filtered, key = { it.number }) { surah ->
            val bookmarked = prefs.bookmarkedSurahs.contains(surah.number)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSurah(surah.number) }
                    .testTag("surah_item_${surah.number}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(NoorDarkGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = surah.number.toString(),
                            color = NoorGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = surah.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${surah.ayahsCount} Ayahs • ${surah.revelationType}",
                            fontSize = 12.sp,
                            color = noor.mutedText
                        )
                    }
                    Text(
                        text = surah.arabicName,
                        fontFamily = AmiriFontFamily,
                        fontSize = 20.sp,
                        color = NoorGold,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { onToggleBookmark(surah.number) }) {
                        Icon(
                            imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark ${surah.name}",
                            tint = if (bookmarked) NoorGold else noor.mutedText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SurahReaderScreen(
    surahNumber: Int,
    uiState: NoorUiState,
    onBack: () -> Unit,
    onToggleBookmark: (Int) -> Unit,
    onToggleAudio: (String, String, Boolean) -> Unit
) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    val surah = remember(surahNumber) {
        QuranCatalog.all114Surahs.find { it.number == surahNumber } ?: QuranCatalog.all114Surahs.first()
    }
    val bookmarked = prefs.bookmarkedSurahs.contains(surah.number)
    val fullAudioId = "surah_${surah.number}"
    val isPlayingFull = uiState.playingAudioId == fullAudioId

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = surah.arabicName,
                        fontFamily = AmiriFontFamily,
                        color = NoorGold,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${surah.number}. ${surah.name} (${surah.meaningHindi})",
                        color = NoorWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${surah.revelationType} • ${surah.ayahsCount} Ayahs",
                        color = NoorWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val textToSpeak = surah.verses.joinToString(". ") { it.hindiTarjuma }
                                onToggleAudio(fullAudioId, "${surah.name}. $textToSpeak", false)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NoorGold),
                            modifier = Modifier.testTag("surah_play_audio_button")
                        ) {
                            Icon(
                                imageVector = if (isPlayingFull) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Audio Play",
                                tint = NoorDarkGreen
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isPlayingFull) "Pause" else "Audio Play",
                                color = NoorDarkGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(onClick = { onToggleBookmark(surah.number) }) {
                            Icon(
                                imageVector = if (bookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = NoorGold
                            )
                        }
                        IconButton(onClick = {
                            val summary = surah.verses.joinToString("\n\n") { "${it.arabic}\n${it.hindiTarjuma}" }
                            DeviceActionHelper.copyText(context, surah.name, "${surah.name}\n\n$summary")
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = NoorWhite)
                        }
                        IconButton(onClick = {
                            val summary = surah.verses.joinToString("\n\n") { "${it.arabic}\n${it.hindiTarjuma}" }
                            DeviceActionHelper.shareText(context, "📖 ${surah.name}\n\n$summary")
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = NoorWhite)
                        }
                    }
                }
            }
        }

        items(surah.verses) { verse ->
            val tarjuma = when (prefs.language) {
                AppLanguage.HINDI -> verse.hindiTarjuma
                AppLanguage.URDU -> verse.urduTarjuma
                AppLanguage.ENGLISH -> verse.englishTarjuma
            }
            val verseAudioId = "verse_${surah.number}_${verse.number}"
            val isVersePlaying = uiState.playingAudioId == verseAudioId

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = NoorDarkGreen,
                            shape = CircleShape
                        ) {
                            Text(
                                text = if (verse.number == 0) "﷽" else "${surah.number}:${verse.number}",
                                color = NoorGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Row {
                            IconButton(
                                onClick = { onToggleAudio(verseAudioId, tarjuma, false) },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = if (isVersePlaying) Icons.Default.Pause else Icons.Default.VolumeUp,
                                    contentDescription = "Listen",
                                    tint = NoorGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { DeviceActionHelper.copyText(context, "Ayah", "${verse.arabic}\n$tarjuma") },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = noor.mutedText, modifier = Modifier.size(17.dp))
                            }
                            IconButton(
                                onClick = { DeviceActionHelper.shareText(context, "${verse.arabic}\n\n$tarjuma\n(${surah.name})") },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share", tint = noor.mutedText, modifier = Modifier.size(17.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = verse.arabic,
                        fontFamily = AmiriFontFamily,
                        fontSize = (26 * prefs.fontScale).sp,
                        lineHeight = (42 * prefs.fontScale).sp,
                        color = if (noor.isDark) NoorGold else NoorDarkGreen,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = tarjuma,
                        fontSize = (15 * prefs.fontScale).sp,
                        lineHeight = (22 * prefs.fontScale).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
