package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.NamesAndKidsCatalog
import com.example.data.repository.NoorDataCatalog
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.IslamicDateHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen(uiState: NoorUiState) {
    val noor = LocalNoorColors.current
    var monthOffset by remember { mutableIntStateOf(0) }

    val displayCal = remember(monthOffset) {
        Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MONTH, monthOffset)
        }
    }
    val monthTitle = remember(displayCal) {
        SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(displayCal.time)
    }
    val hijriMonthRange = remember(displayCal) {
        val startH = IslamicDateHelper.getHijriDate(displayCal.time)
        val endCal = (displayCal.clone() as Calendar).apply {
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        val endH = IslamicDateHelper.getHijriDate(endCal.time)
        "$startH  —  $endH"
    }

    val daysInMonth = displayCal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = displayCal.get(Calendar.DAY_OF_WEEK) - 1 // 0..6 (Sun..Sat)
    val todayCal = remember { Calendar.getInstance() }
    val isCurrentMonth = todayCal.get(Calendar.YEAR) == displayCal.get(Calendar.YEAR) &&
        todayCal.get(Calendar.MONTH) == displayCal.get(Calendar.MONTH)

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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Aaj ki Hijri Tarikh",
                        color = NoorWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = uiState.hijriDate,
                        color = NoorGold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = uiState.gregorianFull,
                        color = NoorWhite,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Dual Month Grid
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { monthOffset-- }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous Month", tint = NoorGold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(monthTitle, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            Text(hijriMonthRange, fontSize = 10.sp, color = NoorGold, textAlign = TextAlign.Center)
                        }
                        IconButton(onClick = { monthOffset++ }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next Month", tint = NoorGold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { d ->
                            Text(
                                text = d,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (d == "Fri") NoorGold else noor.mutedText
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    val totalCells = firstDayOfWeek + daysInMonth
                    val rows = (totalCells + 6) / 7
                    for (r in 0 until rows) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                        ) {
                            for (c in 0 until 7) {
                                val cellIndex = r * 7 + c
                                val dayNum = cellIndex - firstDayOfWeek + 1
                                if (dayNum in 1..daysInMonth) {
                                    val cellCal = (displayCal.clone() as Calendar).apply {
                                        set(Calendar.DAY_OF_MONTH, dayNum)
                                    }
                                    val hDay = IslamicDateHelper.getHijriDayNumber(cellCal.time)
                                    val isToday = isCurrentMonth && dayNum == todayCal.get(Calendar.DAY_OF_MONTH)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isToday) NoorDarkGreen else Color.Transparent)
                                            .border(
                                                width = if (isToday) 1.5.dp else 0.dp,
                                                color = if (isToday) NoorGold else Color.Transparent,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = dayNum.toString(),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isToday) NoorWhite else MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = hDay.toString(),
                                                fontSize = 9.sp,
                                                color = NoorGold,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Important Islamic Dates & Holidays",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        items(NoorDataCatalog.islamicEvents) { ev ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (ev.highlight) noor.goldSoft else noor.cardSurface
                ),
                border = BorderStroke(1.dp, if (ev.highlight) NoorGold else noor.borderColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(ev.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(ev.description, fontSize = 12.sp, color = noor.mutedText)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Surface(color = NoorDarkGreen, shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = ev.gregorianDate,
                                color = NoorGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(ev.hijriDate, fontSize = 10.sp, color = noor.mutedText)
                    }
                }
            }
        }
    }
}

@Composable
fun AllahNamesScreen(
    uiState: NoorUiState,
    onToggleFavName: (Int) -> Unit,
    onToggleAudio: (String, String, Boolean) -> Unit
) {
    val noor = LocalNoorColors.current
    val prefs = uiState.prefs
    var query by remember { mutableStateOf("") }
    var onlyFav by remember { mutableStateOf(false) }

    val list = remember(query, onlyFav, prefs.favouriteNames) {
        NamesAndKidsCatalog.all99Names.filter { item ->
            val qOk = query.isBlank() ||
                item.transliteration.contains(query, ignoreCase = true) ||
                item.hindiMeaning.contains(query, ignoreCase = true) ||
                item.number.toString() == query.trim()
            val fOk = !onlyFav || prefs.favouriteNames.contains(item.number)
            qOk && fOk
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search 99 Names (Ar-Rahman, Al-Malik...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("names_search_input")
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = !onlyFav,
                    onClick = { onlyFav = false },
                    label = { Text("All 99 Names") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NoorDarkGreen, selectedLabelColor = NoorGold)
                )
                FilterChip(
                    selected = onlyFav,
                    onClick = { onlyFav = true },
                    label = { Text("Favourites (${prefs.favouriteNames.size})") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = NoorDarkGreen, selectedLabelColor = NoorGold)
                )
            }
        }

        items(list, key = { it.number }) { item ->
            val isFav = prefs.favouriteNames.contains(item.number)
            val audioId = "name_${item.number}"
            val isPlaying = uiState.playingAudioId == audioId

            Card(
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.7f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(NoorDeepEmerald, NoorDarkGreen)))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(NoorGold),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.number.toString(),
                                        color = NoorDarkGreen,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.transliteration,
                                        color = NoorWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = item.hindiMeaning,
                                        color = NoorGold,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Text(
                                text = item.arabic,
                                fontFamily = AmiriFontFamily,
                                fontSize = 28.sp,
                                color = NoorGold,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "फ़ायदा: ${item.benefitHindi}",
                                color = NoorWhite.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Row {
                                IconButton(
                                    onClick = {
                                        onToggleAudio(
                                            audioId,
                                            "${item.transliteration}. ${item.hindiMeaning}. ${item.benefitHindi}",
                                            false
                                        )
                                    },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp,
                                        contentDescription = "Audio ${item.transliteration}",
                                        tint = NoorGold
                                    )
                                }
                                IconButton(
                                    onClick = { onToggleFavName(item.number) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favourite",
                                        tint = if (isFav) noor.rose else NoorWhite.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
