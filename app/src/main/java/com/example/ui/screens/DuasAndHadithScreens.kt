package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.NoorDataCatalog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.DeviceActionHelper

@Composable
fun DuasScreen(
    uiState: NoorUiState,
    onToggleFavDua: (Int) -> Unit,
    onToggleAudio: (String, String, Boolean) -> Unit
) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("All", "Rozmarra", "Subah", "Sham", "Jummah", "Safar", "Ibadat", "Favourites")
    val filteredDuas = remember(selectedCategory, searchQuery, prefs.favouriteDuas) {
        NoorDataCatalog.duas.filter { dua ->
            val catOk = when (selectedCategory) {
                "All" -> true
                "Favourites" -> prefs.favouriteDuas.contains(dua.id)
                else -> dua.category == selectedCategory
            }
            val queryOk = searchQuery.isBlank() ||
                dua.title.contains(searchQuery, ignoreCase = true) ||
                dua.transliteration.contains(searchQuery, ignoreCase = true) ||
                dua.hindiTarjuma.contains(searchQuery, ignoreCase = true)
            catOk && queryOk
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search Dua (Sone, Safar, Exam, Rizq...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NoorDarkGreen,
                            selectedLabelColor = NoorGold
                        )
                    )
                }
            }
        }

        items(filteredDuas, key = { it.id }) { dua ->
            val isFav = prefs.favouriteDuas.contains(dua.id)
            val audioId = "dua_${dua.id}"
            val isPlaying = uiState.playingAudioId == audioId
            val tarjuma = when (prefs.language) {
                AppLanguage.HINDI -> dua.hindiTarjuma
                AppLanguage.URDU -> dua.urduTarjuma
                AppLanguage.ENGLISH -> dua.englishTarjuma
            }

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(color = NoorDarkGreen, shape = RoundedCornerShape(8.dp)) {
                                Text(
                                    text = "${dua.id}. ${dua.category}",
                                    color = NoorGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dua.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(onClick = { onToggleFavDua(dua.id) }) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favourite Dua",
                                tint = if (isFav) noor.rose else noor.mutedText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dua.arabic,
                        fontFamily = AmiriFontFamily,
                        fontSize = (24 * prefs.fontScale).sp,
                        lineHeight = (38 * prefs.fontScale).sp,
                        color = if (noor.isDark) NoorGold else NoorDarkGreen,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "पढ़ने का तरीक़ा: ${dua.transliteration}",
                        fontSize = (13 * prefs.fontScale).sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NoorGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tarjuma,
                        fontSize = (14 * prefs.fontScale).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            onToggleAudio(audioId, "${dua.title}. ${dua.transliteration}. $tarjuma", false)
                        }) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp,
                                contentDescription = "Listen Dua",
                                tint = NoorGold
                            )
                        }
                        IconButton(onClick = {
                            DeviceActionHelper.copyText(
                                context,
                                dua.title,
                                "${dua.title}\n${dua.arabic}\n${dua.transliteration}\n$tarjuma"
                            )
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy Dua", tint = noor.mutedText)
                        }
                        IconButton(onClick = {
                            DeviceActionHelper.shareText(
                                context,
                                "🤲 ${dua.title}\n\n${dua.arabic}\n${dua.transliteration}\n\n$tarjuma"
                            )
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Share Dua", tint = noor.mutedText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HadithScreen(
    uiState: NoorUiState,
    onToggleFavHadith: (Int) -> Unit,
    onToggleMorningNotif: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    var onlyFav by remember { mutableStateOf(false) }

    val list = remember(onlyFav, prefs.favouriteHadiths) {
        if (!onlyFav) NoorDataCatalog.hadiths
        else NoorDataCatalog.hadiths.filter { prefs.favouriteHadiths.contains(it.id) }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = NoorGold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Roz Subah Hadith Notification", color = NoorWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Har roz subah 7:00 AM par ek Hadith paayein", color = NoorWhite.copy(alpha = 0.78f), fontSize = 11.sp)
                        }
                    }
                    Switch(
                        checked = prefs.morningHadithNotif,
                        onCheckedChange = onToggleMorningNotif,
                        colors = SwitchDefaults.colors(checkedThumbColor = NoorGold, checkedTrackColor = NoorGold.copy(alpha = 0.4f))
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = !onlyFav,
                    onClick = { onlyFav = false },
                    label = { Text("All 20 Hadiths") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NoorDarkGreen, selectedLabelColor = NoorGold)
                )
                FilterChip(
                    selected = onlyFav,
                    onClick = { onlyFav = true },
                    label = { Text("Favourites (${prefs.favouriteHadiths.size})") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NoorDarkGreen, selectedLabelColor = NoorGold)
                )
            }
        }

        items(list, key = { it.id }) { hadith ->
            val isFav = prefs.favouriteHadiths.contains(hadith.id)
            val body = when (prefs.language) {
                AppLanguage.HINDI -> hadith.textHindi
                AppLanguage.URDU -> hadith.textUrdu
                AppLanguage.ENGLISH -> hadith.textEnglish
            }

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
                        Surface(color = NoorGold.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp)) {
                            Text(
                                text = "Hadith #${hadith.id} • ${hadith.reference}",
                                color = if (noor.isDark) NoorGold else NoorDarkGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Row {
                            IconButton(
                                onClick = { onToggleFavHadith(hadith.id) },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favourite Hadith",
                                    tint = if (isFav) noor.rose else noor.mutedText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { DeviceActionHelper.copyText(context, "Hadith", "${hadith.arabic}\n$body\n— ${hadith.reference}") },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = noor.mutedText, modifier = Modifier.size(17.dp))
                            }
                            IconButton(
                                onClick = { DeviceActionHelper.shareText(context, "📖 Hadith #${hadith.id}\n\n${hadith.arabic}\n\n$body\n— ${hadith.reference}") },
                                modifier = Modifier
                                    .size(34.dp)
                                    .testTag("hadith_share_${hadith.id}")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share", tint = noor.mutedText, modifier = Modifier.size(17.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = hadith.arabic,
                        fontFamily = AmiriFontFamily,
                        fontSize = (20 * prefs.fontScale).sp,
                        color = NoorGold,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = body,
                        fontSize = (15 * prefs.fontScale).sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
