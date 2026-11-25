package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.CityInfo
import com.example.data.repository.NoorDataCatalog
import com.example.ui.theme.LocalNoorColors
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite

/**
 * Sticky Green Patti Header displayed at the top of every screen.
 * Left: 📍 [City Name] (clickable to switch city or detect GPS)
 * Right: Date + Mausam + Optional Dark Mode quick toggle
 */
@Composable
fun NoorStickyTopHeader(
    city: CityInfo,
    isGpsDetected: Boolean,
    dateShort: String,
    isDark: Boolean,
    showBackButton: Boolean = false,
    pageTitle: String? = null,
    onBackClick: () -> Unit = {},
    onCityClick: () -> Unit,
    onToggleDark: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = NoorDarkGreen,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(NoorDeepEmerald, NoorDarkGreen)
                    )
                )
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (showBackButton) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("top_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = NoorGold
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.12f))
                            .clickable(onClick = onCityClick)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("header_city_chip"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "City Location",
                            tint = NoorGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isGpsDetected) "${city.name} (GPS)" else city.name,
                            color = NoorWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = dateShort,
                            color = NoorGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                        Text(
                            text = "${city.weatherTemp} • ${city.weatherCond}",
                            color = NoorWhite.copy(alpha = 0.9f),
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onToggleDark,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("header_dark_mode_toggle")
                    ) {
                        Icon(
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Dark Mode",
                            tint = NoorGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            if (!pageTitle.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = pageTitle,
                    color = NoorGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

/**
 * Bottom Navigation Bar (Always visible on main screens)
 */
@Composable
fun NoorBottomBar(
    currentRoute: String,
    language: AppLanguage,
    onNavigate: (String) -> Unit
) {
    val noor = LocalNoorColors.current
    val items = listOf(
        Triple("home", NoorStrings.home(language), Pair(Icons.Filled.Home, Icons.Outlined.Home)),
        Triple("quran", NoorStrings.quran(language), Pair(Icons.Filled.Book, Icons.Outlined.Book)),
        Triple("namaz", NoorStrings.namaz(language), Pair(Icons.Filled.Schedule, Icons.Outlined.Schedule)),
        Triple("tasbeeh", NoorStrings.tasbeeh(language), Pair(Icons.Filled.RadioButtonChecked, Icons.Outlined.RadioButtonChecked)),
        Triple("more", NoorStrings.more(language), Pair(Icons.Filled.GridView, Icons.Outlined.GridView))
    )

    NavigationBar(
        containerColor = if (noor.isDark) NoorDeepEmerald else NoorWhite,
        contentColor = NoorDarkGreen,
        tonalElevation = 8.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        items.forEach { (route, label, icons) ->
            val selected = currentRoute == route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(route) },
                modifier = Modifier.testTag("nav_$route"),
                icon = {
                    Icon(
                        imageVector = if (selected) icons.first else icons.second,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NoorDarkGreen,
                    selectedTextColor = if (noor.isDark) NoorGold else NoorDarkGreen,
                    indicatorColor = NoorGold.copy(alpha = 0.28f),
                    unselectedIconColor = noor.mutedText,
                    unselectedTextColor = noor.mutedText
                )
            )
        }
    }
}

/**
 * Small Adsterra Banner Slot at the bottom of every screen
 */
@Composable
fun AdsterraBannerSlot(modifier: Modifier = Modifier) {
    val noor = LocalNoorColors.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        color = if (noor.isDark) NoorDarkGreen.copy(alpha = 0.55f) else noor.subtleSurface,
        border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = NoorGold,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "AD",
                        color = NoorDarkGreen,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Adsterra Banner Slot • Free Islamic App",
                    style = MaterialTheme.typography.labelSmall,
                    color = noor.mutedText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = "Noor",
                color = NoorGold,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityPickerSheet(
    selectedCity: CityInfo,
    onDismiss: () -> Unit,
    onSelectCity: (String) -> Unit,
    onDetectGps: () -> Unit
) {
    val noor = LocalNoorColors.current
    var query by remember { mutableStateOf("") }
    val filtered = remember(query) {
        if (query.isBlank()) NoorDataCatalog.cities
        else NoorDataCatalog.cities.filter {
            it.name.contains(query, ignoreCase = true) ||
                it.displaySub.contains(query, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📍 Apna Shehar Chunein",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedButton(
                    onClick = {
                        onDetectGps()
                        onDismiss()
                    },
                    border = BorderStroke(1.dp, NoorGold),
                    modifier = Modifier.testTag("gps_detect_button")
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "Detect GPS", tint = NoorGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Auto GPS", fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search City (Thakurdwara, Delhi, Dubai...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("city_picker_search")
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(modifier = Modifier.height(340.dp)) {
                items(filtered) { city ->
                    val active = city.name == selectedCity.name
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (active) NoorGold.copy(alpha = 0.16f) else Color.Transparent)
                            .clickable {
                                onSelectCity(city.name)
                                onDismiss()
                            }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = city.name,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${city.displaySub} • ${city.weatherTemp} • Fajr ${city.fajr}",
                                style = MaterialTheme.typography.labelSmall,
                                color = noor.mutedText
                            )
                        }
                        if (active) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = NoorGold)
                        }
                    }
                    HorizontalDivider(color = noor.borderColor.copy(alpha = 0.5f))
                }
            }
        }
    }
}

@Composable
fun NameEditDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var nameText by remember { mutableStateOf(currentName) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Assalamualaikum! Aapka Naam Kya Hai?",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Koi Email, Password ya OTP nahi. Sirf aapka pyaara naam likhein:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    label = { Text("Aapka Naam") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(nameText.ifBlank { "Bhaijaan" }) },
                colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen)
            ) {
                Text("Save", color = NoorWhite)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
