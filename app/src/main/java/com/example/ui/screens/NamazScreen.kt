package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.NoorDataCatalog
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.viewmodel.NoorUiState
import com.example.util.IslamicDateHelper

@Composable
fun NamazScreen(
    uiState: NoorUiState,
    onSelectCity: (String) -> Unit,
    onDetectGps: () -> Unit,
    onToggleAlarm: (String) -> Unit,
    onTestNotification: () -> Unit,
    onToggleTodayPrayer: (String) -> Unit,
    onAdjustQaza: (String, Int) -> Unit
) {
    val noor = LocalNoorColors.current
    val city = uiState.activeCity
    val prefs = uiState.prefs
    val prayers = remember(city) { IslamicDateHelper.getCityPrayerEntries(city) }
    val monthRows = remember(city) { IslamicDateHelper.generate30DayTable(city) }

    var citySearch by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Today & Alarms, 1 = 30 Din Table, 2 = Qaza Tracker

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onDetectGps()
    }

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onTestNotification()
    }

    val filteredCities = remember(citySearch) {
        if (citySearch.isBlank()) NoorDataCatalog.cities
        else NoorDataCatalog.cities.filter { it.name.contains(citySearch, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // City Search & GPS
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Namaz Time — ${city.name}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Method: University of Islamic Sciences, Karachi (Hanafi)",
                                style = MaterialTheme.typography.labelSmall,
                                color = NoorGold,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        OutlinedButton(
                            onClick = { locationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
                            border = BorderStroke(1.dp, NoorGold),
                            modifier = Modifier.testTag("namaz_gps_button")
                        ) {
                            Icon(Icons.Default.MyLocation, contentDescription = "GPS", tint = NoorGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("GPS", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = citySearch,
                        onValueChange = { citySearch = it },
                        placeholder = { Text("Search City (Thakurdwara, Moradabad, Delhi, London...)") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("namaz_city_search")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        filteredCities.forEach { c ->
                            val selected = c.name == city.name
                            FilterChip(
                                selected = selected,
                                onClick = { onSelectCity(c.name) },
                                label = { Text(c.name, fontSize = 12.sp) },
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

        // Mode Tabs: Today & Azan | 30 Din Table | Qaza Tracker
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Aaj & Azan", "30 Din Table", "Qaza Tracker").forEachIndexed { idx, title ->
                    val active = selectedTab == idx
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (active) NoorDarkGreen else noor.cardSurface,
                        border = BorderStroke(1.dp, if (active) NoorGold else noor.borderColor),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = idx }
                            .testTag("namaz_tab_$idx")
                    ) {
                        Text(
                            text = title,
                            color = if (active) NoorGold else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }

        if (selectedTab == 0) {
            // Sunrise / Sehri / Iftar banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Sehri", color = NoorWhite.copy(alpha = 0.8f), fontSize = 11.sp)
                            Text(IslamicDateHelper.format12Hour(city.sehri), color = NoorGold, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Sunrise", color = NoorWhite.copy(alpha = 0.8f), fontSize = 11.sp)
                            Text(IslamicDateHelper.format12Hour(city.sunrise), color = NoorWhite, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Iftar", color = NoorWhite.copy(alpha = 0.8f), fontSize = 11.sp)
                            Text(IslamicDateHelper.format12Hour(city.iftar), color = NoorGold, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Prayer Cards with 10-min-before Azan Alarm & Today Tick
            items(prayers) { p ->
                val alarmOn = prefs.azanAlarms.contains(p.key)
                val todayTicked = prefs.todayPrayedDate == IslamicDateHelper.getTodayKey() &&
                    prefs.todayPrayedSet.contains(p.key)
                val isNext = p.key == uiState.nextPrayer.prayerKey

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isNext) noor.goldSoft else noor.cardSurface
                    ),
                    border = BorderStroke(1.dp, if (isNext) NoorGold else noor.borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            IconButton(
                                onClick = { onToggleTodayPrayer(p.key) },
                                modifier = Modifier.testTag("prayer_tick_${p.key}")
                            ) {
                                Icon(
                                    imageVector = if (todayTicked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Tick ${p.key}",
                                    tint = if (todayTicked) noor.success else noor.mutedText
                                )
                            }
                            Column {
                                Text(
                                    text = "${p.key} (${p.nameHindi})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = p.timeDisplay,
                                    color = NoorGold,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Alarm,
                                contentDescription = null,
                                tint = if (alarmOn) NoorDarkGreen else noor.mutedText,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("10m", fontSize = 11.sp, color = noor.mutedText)
                            Spacer(modifier = Modifier.width(6.dp))
                            Switch(
                                checked = alarmOn,
                                onCheckedChange = { onToggleAlarm(p.key) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = NoorGold,
                                    checkedTrackColor = NoorDarkGreen
                                )
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            onTestNotification()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("test_azan_notification_button")
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = NoorGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Test Azan Notification (10 Min Alert)", color = NoorWhite, fontWeight = FontWeight.Bold)
                }
            }
        } else if (selectedTab == 1) {
            // 30 Din Full Month Table
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NoorDarkGreen)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Date", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
                        Text("Fajr", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Dhuhr", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Asr", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Magh", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Isha", color = NoorGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    }
                }
            }
            items(monthRows) { row ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (row.isToday) noor.goldSoft else noor.cardSurface
                    ),
                    border = BorderStroke(1.dp, if (row.isToday) NoorGold else noor.borderColor.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1.2f)) {
                            Text(row.gregorianShort, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(row.hijriShort, fontSize = 9.sp, color = noor.mutedText)
                        }
                        Text(row.fajr.substringBefore(" "), fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(IslamicDateHelper.format12Hour(row.dhuhr).substringBefore(" "), fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(row.asr.substringBefore(" "), fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(row.maghrib.substringBefore(" "), fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(row.isha.substringBefore(" "), fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    }
                }
            }
        } else {
            // Qaza Tracker
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                    border = BorderStroke(1.dp, noor.borderColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Qaza Namaz Tracker",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Agar koi Namaz chhoot gayi ho to (+) dabayein, aur Qaza ada karne par (-) dabayein.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = noor.mutedText
                        )
                    }
                }
            }

            val qazaList = listOf(
                "Fajr" to prefs.qazaFajr,
                "Dhuhr" to prefs.qazaDhuhr,
                "Asr" to prefs.qazaAsr,
                "Maghrib" to prefs.qazaMaghrib,
                "Isha" to prefs.qazaIsha
            )
            items(qazaList) { (prayerKey, count) ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                    border = BorderStroke(1.dp, noor.borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(prayerKey, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Baki Qaza: $count", fontSize = 12.sp, color = NoorGold, fontWeight = FontWeight.SemiBold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { onAdjustQaza(prayerKey, -1) }) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease Qaza", tint = NoorDarkGreen)
                            }
                            Text(
                                text = count.toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            IconButton(onClick = { onAdjustQaza(prayerKey, 1) }) {
                                Icon(Icons.Default.Add, contentDescription = "Add Qaza", tint = NoorDarkGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}
