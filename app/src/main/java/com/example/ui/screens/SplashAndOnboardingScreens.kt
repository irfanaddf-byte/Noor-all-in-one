package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.NoorDarkGreen
import com.example.ui.theme.NoorDeepEmerald
import com.example.ui.theme.NoorGold
import com.example.ui.theme.NoorWhite
import com.example.ui.theme.PlayfairFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, animationSpec = tween(700))
        delay(1300L)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(NoorDeepEmerald, NoorDarkGreen, NoorDeepEmerald))
            ),
        contentAlignment = Alignment.Center
    ) {
        // Subtle Islamic geometric rings
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            drawCircle(
                color = NoorGold.copy(alpha = 0.08f),
                radius = size.minDimension * 0.42f,
                center = Offset(cx, cy),
                style = Stroke(width = 2.dp.toPx())
            )
            drawCircle(
                color = NoorGold.copy(alpha = 0.05f),
                radius = size.minDimension * 0.56f,
                center = Offset(cx, cy),
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        Column(
            modifier = Modifier
                .padding(32.dp)
                .alpha(alphaAnim.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                fontFamily = AmiriFontFamily,
                color = NoorGold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_noor_icon_src),
                contentDescription = "Noor App Emblem",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Noor - All In One",
                fontFamily = PlayfairFontFamily,
                color = NoorGold,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "By Thakurdwara",
                color = NoorWhite.copy(alpha = 0.9f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Quran • Namaz • Duas • Qibla • Tasbeeh",
                color = NoorGold.copy(alpha = 0.85f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

private data class OnboardingSlide(
    val title: String,
    val subtitle: String,
    val badge: String,
    val imageRes: Int
)

@Composable
fun OnboardingScreen(
    initialName: String,
    onComplete: (String) -> Unit
) {
    val slides = remember {
        listOf(
            OnboardingSlide(
                title = "Namaz Time & Qibla",
                subtitle = "Apne shehar aur GPS ke mutabik bilkul sahi Namaz ke waqt (Karachi Hanafi), 10 minute pehle Azan Alarm aur Qibla Compass.",
                badge = "Slide 1 • Namaz Time",
                imageRes = R.drawable.img_mosque_hero
            ),
            OnboardingSlide(
                title = "Al-Quran Al-Kareem",
                subtitle = "114 Surahs ki poori list, bada saaf Arabic text, Hindi Tarjuma, Audio playback aur Bookmark — bina Internet ke bhi chale!",
                badge = "Slide 2 • Quran",
                imageRes = R.drawable.img_quran_art
            ),
            OnboardingSlide(
                title = "Masnoon Duas & Tasbeeh",
                subtitle = "Rozmarra ki Duas, 20 Hadiths, 99 Names, Zakat Calculator, Kids Section aur Vibration ke saath Digital Tasbeeh.",
                badge = "Slide 3 • Duas & Zikr",
                imageRes = R.drawable.img_lantern_art
            )
        )
    }
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val scope = rememberCoroutineScope()
    var nameInput by remember { mutableStateOf(initialName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(NoorDeepEmerald, NoorDarkGreen))
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Noor - All In One",
                color = NoorGold,
                fontFamily = PlayfairFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { onComplete(nameInput.ifBlank { "Bhaijaan" }) }) {
                Text("Skip", color = NoorWhite.copy(alpha = 0.85f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) { page ->
            val slide = slides[page]
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = slide.imageRes),
                        contentDescription = slide.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = slide.badge,
                            color = NoorGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = slide.title,
                            color = NoorWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = slide.subtitle,
                            color = NoorWhite.copy(alpha = 0.88f),
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(slides.size) { idx ->
                val active = pagerState.currentPage == idx
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(8.dp)
                        .width(if (active) 24.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (active) NoorGold else NoorWhite.copy(alpha = 0.35f))
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // First-Time Name Prompt (No Email, No Password, No OTP!)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = NoorWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Assalamualaikum! Aapka Naam Kya Hai?",
                    color = NoorDarkGreen,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "No Login • No Email • No Password • No OTP",
                    color = NoorGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    placeholder = { Text("Apna Naam likhein (e.g. Irfan)") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = NoorDarkGreen) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NoorDarkGreen,
                        unfocusedBorderColor = NoorDarkGreen.copy(alpha = 0.35f),
                        focusedTextColor = NoorDarkGreen,
                        unfocusedTextColor = NoorDarkGreen
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("onboarding_name_input")
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage < slides.lastIndex) {
                        Button(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen.copy(alpha = 0.12f)),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Next Slide", color = NoorDarkGreen, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NoorDarkGreen, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Button(
                        onClick = { onComplete(nameInput.ifBlank { "Bhaijaan" }) },
                        colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("onboarding_start_button")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = NoorGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Bismillah • Start", color = NoorWhite, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
