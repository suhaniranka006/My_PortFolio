package com.example.my_portfolio

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF1A1A1A))
    )

    // --- Contact Items Data ---
    // Make sure you have added these vector assets to your res/drawable folder
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
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideInVertically(animationSpec = tween(durationMillis = 500))
            ) {
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

                    // Typewriter Effect Logic
                    var displayedTitle by remember { mutableStateOf("") }
                    var cursorVisible by remember { mutableStateOf(true) }
                    LaunchedEffect(yourTitle) {
                        yourTitle.forEachIndexed { index, _ ->
                            displayedTitle = yourTitle.substring(0, index + 1)
                            delay(100)
                        }
                        while (true) {
                            cursorVisible = !cursorVisible
                            delay(500)
                        }
                    }

                    Text(
                        text = displayedTitle + if (cursorVisible) "_" else "",
                        fontSize = 16.sp,
                        color = Color.LightGray,
                        modifier = Modifier.height(24.dp) // Stable height for animation
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // --- Contact Info Items ---
        items(contactItems.size) { index ->
            val item = contactItems[index]
            var itemVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(200L + (index * 100L))
                itemVisible = true
            }

            AnimatedVisibility(
                visible = itemVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 100)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(durationMillis = 500, delayMillis = 100)
                        )
            ) {
                ContactInfoItem(
                    icon = item.first,
                    label = item.second,
                    value = item.third,
                    // Pass true to enable animation for GitHub and WhatsApp
                    isHighlighted = item.second == "GitHub" || item.second == "WhatsApp",
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
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- Resume Button ---
        item {
            var itemVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(1100L)
                itemVisible = true
            }
            AnimatedVisibility(
                visible = itemVisible,
                enter = fadeIn(tween(500)) + slideInVertically(tween(500), initialOffsetY = { it / 2 })
            ) {
                val buttonGradient = Brush.horizontalGradient(
                    colors = listOf(DarkPink, LightPink, DarkPink)
                )
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(brush = buttonGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Outlined.Visibility, contentDescription = "View Icon", modifier = Modifier.size(24.dp), tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "View My Resume", fontSize = 16.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfoItem(icon: Any, label: String, value: String, isHighlighted: Boolean, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val pressScale by animateFloatAsState(targetValue = if (isPressed) 0.98f else 1f, label = "")

    // --- Animation setup for highlighted items ---
    val infiniteTransition = rememberInfiniteTransition(label = "highlight_transition")

    val pulseScale by if (isHighlighted) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse_scale"
        )
    } else {
        remember { mutableStateOf(1f) }
    }

    val borderShine by if (isHighlighted) {
        infiniteTransition.animateColor(
            initialValue = DarkPink.copy(alpha = 0.8f),
            targetValue = LightPink.copy(alpha = 0.8f),
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "border_shine"
        )
    } else {
        remember { mutableStateOf(DarkPink.copy(alpha = 0.3f)) }
    }

    val cardGradient = Brush.horizontalGradient(
        colors = listOf(DarkPink.copy(alpha = 0.15f), Color.Black.copy(alpha = 0.4f))
    )

    val iconGradient = Brush.verticalGradient(
        colors = listOf(LightPink, DarkPink)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(pressScale * pulseScale) // Combine press and pulse scales
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
        border = BorderStroke(1.5.dp, borderShine) // Use the animated shine color
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

