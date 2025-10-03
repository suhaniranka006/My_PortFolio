package com.example.my_portfolio

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_portfolio.ui.theme.DarkPink
import com.example.my_portfolio.ui.theme.LightPink
import com.example.my_portfolio.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun ContactTab() {
    val context = LocalContext.current

    // --- Your Information ---
    val yourName = "Suhani Ranka"
    val yourTitle = "Android Developer | Tech Enthusiast"
    val yourEmail = "suhaniranka006@gmail.com"
    val yourPhone = "+917851976119"
    val yourWhatsApp = "+917851976119"
    val yourLinkedIn = "https://www.linkedin.com/in/suhani-ranka-a146a4253/"
    val yourGitHub = "https://github.com/suhaniranka006"
    val yourLeetCode = "https://leetcode.com/u/suhani_jain_006/"
    val yourGfg = "https://www.geeksforgeeks.org/user/suhanijavtd5/"
    val resumeUrl = "https://drive.google.com/uc?export=download&id=1eMfJlFWwFoZ3RCjYxiH9CYYM6ts330Yd"

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF1A1A1A))
    )

    // --- Contact Items Data ---
    // Make sure you have added vector assets like 'ic_linkedin.xml' to your res/drawable folder
    val contactItems = listOf(
        Triple(Icons.Default.Email, "Email", yourEmail),
        Triple(R.drawable.linkedin_pic, "LinkedIn", "Connect with me"),
        Triple(R.drawable.github_pic, "GitHub", "View my work"),
        Triple(R.drawable.leetcode_pic, "LeetCode", "Problem Solving Profile"),
        Triple(R.drawable.geeksforgeeks_pic, "GeeksforGeeks", "Coding Profile"),
        Triple(R.drawable.whatsapp_pic, "WhatsApp", "Message me"),
        Triple(Icons.Default.Phone, "Phone", yourPhone)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Profile Header ---
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = yourName, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                // --- Typewriter Effect Logic ---
                var displayedTitle by remember { mutableStateOf("") }
                var cursorVisible by remember { mutableStateOf(true) }

                // Trigger the animation when the composable is first displayed
                LaunchedEffect(yourTitle) {
                    // Typing animation loop
                    yourTitle.forEachIndexed { index, _ ->
                        displayedTitle = yourTitle.substring(0, index + 1)
                        delay(100) // Adjust typing speed here (in milliseconds)
                    }

                    // Blinking cursor loop after typing is done
                    while (true) {
                        cursorVisible = !cursorVisible
                        delay(500) // Adjust cursor blink speed here
                    }
                }

                Text(
                    text = displayedTitle + if (cursorVisible) "_" else "",
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    modifier = Modifier.height(24.dp) // Set a fixed height to prevent layout jumps
                )
            }
        }



        // --- Highlighted Resume Button (now at the top) ---
        item {
            Spacer(modifier = Modifier.height(32.dp))
            ResumeButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // --- Contact Info Items (no entry animation) ---
        items(contactItems, key = { it.second }) { item ->
            ContactInfoItem(
                icon = item.first,
                label = item.second,
                value = item.third,
                onClick = {
                    val intent = when (item.second) {
                        "Email" -> Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$yourEmail"))
                        "LinkedIn" -> Intent(Intent.ACTION_VIEW, Uri.parse(yourLinkedIn))
                        "GitHub" -> Intent(Intent.ACTION_VIEW, Uri.parse(yourGitHub))
                        "LeetCode" -> Intent(Intent.ACTION_VIEW, Uri.parse(yourLeetCode))
                        "GeeksforGeeks" -> Intent(Intent.ACTION_VIEW, Uri.parse(yourGfg))
                        "WhatsApp" -> Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$yourWhatsApp"))
                        "Phone" -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:$yourPhone"))
                        else -> null
                    }
                    intent?.let { context.startActivity(it) }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ResumeButton(onClick: () -> Unit) {
    // Subtle "breathing" animation for the button to draw attention
    val infiniteTransition = rememberInfiniteTransition(label = "resume_button_transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "resume_scale"
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(DarkPink, LightPink, DarkPink)
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .scale(scale), // Apply the breathing animation
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = buttonGradient),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Visibility,
                    contentDescription = "View Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "View My Resume", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}


@Composable
fun ContactInfoItem(icon: Any, label: String, value: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val pressScale by animateFloatAsState(targetValue = if (isPressed) 0.98f else 1f, label = "")

    val cardGradient = Brush.horizontalGradient(
        colors = listOf(DarkPink.copy(alpha = 0.15f), Color.Black.copy(alpha = 0.4f))
    )

    val iconGradient = Brush.verticalGradient(
        colors = listOf(LightPink, DarkPink)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(pressScale) // Only the press scale animation remains
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, DarkPink.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = cardGradient)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(brush = iconGradient),
                    contentAlignment = Alignment.Center
                ) {
                    val iconModifier = Modifier.size(22.dp)
                    when (icon) {
                        is ImageVector -> Icon(imageVector = icon, contentDescription = label, tint = White, modifier = iconModifier)
                        is Int -> Icon(painter = painterResource(id = icon), contentDescription = label, tint = White, modifier = iconModifier)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = label, color = Color.LightGray.copy(alpha = 0.8f), fontSize = 13.sp)
                    Text(text = value, color = White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

