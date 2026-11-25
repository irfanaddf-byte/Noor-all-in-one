package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToHomeScreen
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.util.DeviceActionHelper

@Composable
fun SupportScreen() {
    val context = LocalContext.current
    val noor = LocalNoorColors.current
    var expandedFaq by remember { mutableIntStateOf(-1) }
    var feedbackText by remember { mutableStateOf("") }
    var feedbackSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // About Us Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NoorDarkGreen),
                border = BorderStroke(1.5.dp, NoorGold)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = NoorGold, modifier = Modifier.size(30.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Noor - All In One",
                        color = NoorGold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Thakurdwara se Mohabbat se banayi gayi 💚",
                        color = NoorWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "100% Free Islamic App • Offline Quran, Duas, Tasbeeh, Namaz Times & Kids Section.",
                        color = NoorWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Donate via UPI + Instagram + Add to Home Screen
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, NoorGold.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = NoorGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Donate & Support (Sadaqah-e-Jariyah)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("UPI ID:", fontSize = 11.sp, color = noor.mutedText)
                            Text("irfanaddf-1@okaxis", fontWeight = FontWeight.Bold, color = NoorGold, fontSize = 15.sp)
                        }
                        IconButton(onClick = { DeviceActionHelper.copyText(context, "UPI ID", "irfanaddf-1@okaxis") }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy UPI ID", tint = NoorGold)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(10, 50, 100).forEach { amt ->
                            Button(
                                onClick = { DeviceActionHelper.openUpiDonate(context, amt) },
                                colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("donate_upi_$amt")
                            ) {
                                Text("₹$amt", color = NoorGold, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                }
            }
        }

        // Follow on Instagram + Add to Home Screen + WhatsApp Contact
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { DeviceActionHelper.openUrl(context, "https://instagram.com/noor_app") },
                    colors = ButtonDefaults.buttonColors(containerColor = NoorGold),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("support_instagram_button")
                ) {
                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = NoorDarkGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Follow on Instagram @NOOR.APP_", color = NoorDarkGreen, fontWeight = FontWeight.ExtraBold)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            DeviceActionHelper.shareOnWhatsApp(
                                context,
                                "Assalamualaikum Noor App Team (Thakurdwara), mujhe app ke baare me baat karni hai:"
                            )
                        },
                        border = BorderStroke(1.dp, NoorGold),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, tint = NoorGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp Contact", fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            val ok = DeviceActionHelper.pinToHomeScreen(context)
                            if (!ok) {
                                Toast.makeText(context, "Launcher home shortcut pinning not supported; app icon already installed.", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_to_home_screen_button")
                    ) {
                        Icon(Icons.Default.AddToHomeScreen, contentDescription = null, tint = NoorGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add to Home", color = NoorWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Report Problem / Feedback Form
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, noor.borderColor)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Report Problem / Feedback", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = {
                            feedbackText = it
                            feedbackSubmitted = false
                        },
                        placeholder = { Text("Koi sawal, sujhav ya problem likhein...") },
                        minLines = 2,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (feedbackText.isNotBlank()) {
                                feedbackSubmitted = true
                                DeviceActionHelper.shareOnWhatsApp(context, "Noor App Feedback:\n$feedbackText")
                                feedbackText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NoorDarkGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = NoorGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Feedback", color = NoorWhite, fontWeight = FontWeight.Bold)
                    }
                    if (feedbackSubmitted) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("JazakAllah Khair! Aapka feedback mil gaya.", color = noor.success, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Help & 10 FAQ
        item {
            Text("Help & FAQ (10 Sawal-Jawab)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        itemsIndexed(NoorDataCatalog.faqs) { idx, faq ->
            val expanded = expandedFaq == idx
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = noor.cardSurface),
                border = BorderStroke(1.dp, if (expanded) NoorGold else noor.borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedFaq = if (expanded) -1 else idx }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(faq.question, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = NoorGold
                        )
                    }
                    AnimatedVisibility(visible = expanded) {
                        Text(
                            text = faq.answer,
                            fontSize = 13.sp,
                            color = noor.mutedText,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
