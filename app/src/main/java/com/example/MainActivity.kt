package com.example

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.components.AdsterraBannerSlot
import com.example.ui.components.CityPickerSheet
import com.example.ui.components.NoorBottomBar
import com.example.ui.components.NoorStickyTopHeader
import com.example.ui.screens.AllahNamesScreen
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.DuasScreen
import com.example.ui.screens.HadithScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KidsScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.NaatBayanScreen
import com.example.ui.screens.NamazScreen
import com.example.ui.screens.NearbyMasjidScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.QiblaScreen
import com.example.ui.screens.QuranListScreen
import com.example.ui.screens.RozaTrackerScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SupportScreen
import com.example.ui.screens.SurahReaderScreen
import com.example.ui.screens.TasbeehScreen
import com.example.ui.screens.WallpapersScreen
import com.example.ui.screens.ZakatScreen
import com.example.ui.theme.NoorTheme
import com.example.ui.viewmodel.NoorUiState
import com.example.ui.viewmodel.NoorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NoorViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            NoorTheme(
                darkTheme = uiState.prefs.darkMode,
                fontScale = uiState.prefs.fontScale
            ) {
                NoorRootApp(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }
}

private enum class AppStage { SPLASH, ONBOARDING, MAIN }

@Composable
private fun NoorRootApp(
    uiState: NoorUiState,
    viewModel: NoorViewModel
) {
    var stage by remember { mutableStateOf(AppStage.SPLASH) }

    when (stage) {
        AppStage.SPLASH -> {
            SplashScreen(
                onSplashFinished = {
                    stage = if (uiState.prefs.onboardingCompleted) AppStage.MAIN else AppStage.ONBOARDING
                }
            )
        }

        AppStage.ONBOARDING -> {
            OnboardingScreen(
                initialName = uiState.prefs.userName,
                onComplete = { enteredName ->
                    viewModel.saveUserNameAndFinishOnboarding(enteredName)
                    stage = AppStage.MAIN
                }
            )
        }

        AppStage.MAIN -> {
            NoorMainScaffold(uiState = uiState, viewModel = viewModel)
        }
    }
}

@Composable
private fun NoorMainScaffold(
    uiState: NoorUiState,
    viewModel: NoorViewModel
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"
    var showCityPicker by remember { mutableStateOf(false) }

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.refreshGpsCity()
    }

    LaunchedEffect(Unit) {
        viewModel.refreshGpsCity()
    }

    val topLevelRoutes = setOf("home", "quran", "namaz", "tasbeeh", "more")
    val activeBottomRoute = when {
        currentRoute in topLevelRoutes -> currentRoute
        currentRoute.startsWith("surah/") -> "quran"
        else -> "more"
    }

    val pageTitle = when {
        currentRoute == "home" -> null
        currentRoute == "quran" -> "Al-Quran Al-Kareem (114 Surahs)"
        currentRoute.startsWith("surah/") -> "Surah Reader"
        currentRoute == "namaz" -> "Namaz Time & Qaza"
        currentRoute == "tasbeeh" -> "Digital Tasbeeh"
        currentRoute == "more" -> "More & Settings"
        currentRoute == "duas" -> "Masnoon Duas"
        currentRoute == "qibla" -> "Qibla Compass"
        currentRoute == "hadith" -> "20 Full Hadiths"
        currentRoute == "calendar" -> "Islamic Calendar (Hijri)"
        currentRoute == "names" -> "99 Names of Allah"
        currentRoute == "zakat" -> "Zakat Calculator"
        currentRoute == "wallpapers" -> "Islamic Wallpapers (20)"
        currentRoute == "naat" -> "Naat & Bayan Audio"
        currentRoute == "masjid" -> "Nearby Masjid"
        currentRoute == "roza" -> "Roza Tracker"
        currentRoute == "kids" -> "Kids Section"
        currentRoute == "support" -> "Support, Donate & About"
        else -> "Noor - All In One"
    }

    fun navigateTop(route: String) {
        if (route in topLevelRoutes) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            navController.navigate(route) {
                launchSingleTop = true
            }
        }
    }

    if (showCityPicker) {
        CityPickerSheet(
            selectedCity = uiState.activeCity,
            onDismiss = { showCityPicker = false },
            onSelectCity = { viewModel.selectCity(it) },
            onDetectGps = {
                locationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                viewModel.refreshGpsCity()
            }
        )
    }

    Scaffold(
        topBar = {
            NoorStickyTopHeader(
                city = uiState.activeCity,
                isGpsDetected = uiState.isGpsDetected,
                dateShort = uiState.gregorianShort,
                isDark = uiState.prefs.darkMode,
                showBackButton = currentRoute !in topLevelRoutes,
                pageTitle = pageTitle,
                onBackClick = { navController.popBackStack() },
                onCityClick = { showCityPicker = true },
                onToggleDark = { viewModel.toggleDarkMode(!uiState.prefs.darkMode) }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                AdsterraBannerSlot()
                NoorBottomBar(
                    currentRoute = activeBottomRoute,
                    language = uiState.prefs.language,
                    onNavigate = { route -> navigateTop(route) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                composable("home") {
                    HomeScreen(
                        uiState = uiState,
                        onNavigate = { route -> navigateTop(route) },
                        onUpdateName = { viewModel.updateUserName(it) },
                        onUpdatePhoto = { viewModel.updateUserPhoto(it) }
                    )
                }

                composable("quran") {
                    QuranListScreen(
                        uiState = uiState,
                        onOpenSurah = { num ->
                            viewModel.setLastReadSurah(num)
                            navController.navigate("surah/$num")
                        },
                        onToggleBookmark = { viewModel.toggleSurahBookmark(it) }
                    )
                }

                composable(
                    route = "surah/{number}",
                    arguments = listOf(navArgument("number") { type = NavType.IntType })
                ) { entry ->
                    val num = entry.arguments?.getInt("number") ?: 1
                    SurahReaderScreen(
                        surahNumber = num,
                        uiState = uiState,
                        onBack = {
                            viewModel.stopAudio()
                            navController.popBackStack()
                        },
                        onToggleBookmark = { viewModel.toggleSurahBookmark(it) },
                        onToggleAudio = { id, text, isAr -> viewModel.togglePlayAudio(id, text, isAr) }
                    )
                }

                composable("namaz") {
                    NamazScreen(
                        uiState = uiState,
                        onSelectCity = { viewModel.selectCity(it) },
                        onDetectGps = { viewModel.refreshGpsCity() },
                        onToggleAlarm = { viewModel.toggleAzanAlarm(it) },
                        onTestNotification = { viewModel.sendTestAzanNotification() },
                        onToggleTodayPrayer = { viewModel.toggleTodayPrayer(it) },
                        onAdjustQaza = { key, delta -> viewModel.adjustQaza(key, delta) }
                    )
                }

                composable("tasbeeh") {
                    TasbeehScreen(
                        uiState = uiState,
                        onTap = { viewModel.tapTasbeeh() },
                        onSelectZikr = { viewModel.selectZikr(it) },
                        onSelectTarget = { viewModel.selectTasbeehTarget(it) },
                        onSaveAndReset = { viewModel.saveAndResetTasbeeh() },
                        onToggleVibration = { viewModel.setVibration(it) }
                    )
                }

                composable("more") {
                    MoreScreen(
                        uiState = uiState,
                        onNavigate = { route -> navigateTop(route) },
                        onToggleDarkMode = { viewModel.toggleDarkMode(it) },
                        onSetFontScale = { viewModel.setFontScale(it) },
                        onSetLanguage = { viewModel.setLanguage(it) },
                        onToggleVibration = { viewModel.setVibration(it) },
                        onToggleMorningNotif = { viewModel.setMorningHadithNotif(it) }
                    )
                }

                composable("duas") {
                    DuasScreen(
                        uiState = uiState,
                        onToggleFavDua = { viewModel.toggleFavDua(it) },
                        onToggleAudio = { id, text, isAr -> viewModel.togglePlayAudio(id, text, isAr) }
                    )
                }

                composable("qibla") {
                    QiblaScreen(
                        uiState = uiState,
                        onDetectGps = { viewModel.refreshGpsCity() }
                    )
                }

                composable("hadith") {
                    HadithScreen(
                        uiState = uiState,
                        onToggleFavHadith = { viewModel.toggleFavHadith(it) },
                        onToggleMorningNotif = { viewModel.setMorningHadithNotif(it) }
                    )
                }

                composable("calendar") {
                    CalendarScreen(uiState = uiState)
                }

                composable("names") {
                    AllahNamesScreen(
                        uiState = uiState,
                        onToggleFavName = { viewModel.toggleFavName(it) },
                        onToggleAudio = { id, text, isAr -> viewModel.togglePlayAudio(id, text, isAr) }
                    )
                }

                composable("zakat") {
                    ZakatScreen(
                        uiState = uiState,
                        onCalculate = { g, s, b, c, gr, sr ->
                            viewModel.calculateZakat(g, s, b, c, gr, sr)
                        }
                    )
                }

                composable("wallpapers") {
                    WallpapersScreen()
                }

                composable("naat") {
                    NaatBayanScreen(
                        uiState = uiState,
                        onToggleAudio = { id, text, isAr -> viewModel.togglePlayAudio(id, text, isAr) }
                    )
                }

                composable("masjid") {
                    NearbyMasjidScreen(uiState = uiState)
                }

                composable("roza") {
                    RozaTrackerScreen(
                        uiState = uiState,
                        onToggleRozaDay = { viewModel.toggleRozaDay(it) },
                        onAddNafliRoza = { viewModel.addNafliRoza() }
                    )
                }

                composable("kids") {
                    KidsScreen(
                        uiState = uiState,
                        onToggleAudio = { id, text, isAr -> viewModel.togglePlayAudio(id, text, isAr) }
                    )
                }

                composable("support") {
                    SupportScreen()
                }
            }
        }
    }
}
