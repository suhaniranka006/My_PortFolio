package com.example.my_portfolio

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
    val yourLeetCode = "https://leetcode.com/u/suhani_jain_006/" // Added LeetCode URL
    val yourGfg = "https://www.geeksforgeeks.org/user/suhanijavtd5/"    // Added GFG URL
    val resumeUrl = "https://drive.google.com/uc?export=download&id=1eMfJlFWwFoZ3RCjYxiH9CYYM6ts330Yd"

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF1A1A1A))
    )

    // --- Contact Items ---
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
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = yourName, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = yourTitle, fontSize = 16.sp, color = Color.LightGray)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // --- Contact Info Items ---
        items(contactItems.size) { index ->
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
                    icon = contactItems[index].first,
                    label = contactItems[index].second,
                    value = contactItems[index].third,
                    onClick = {
                        val intent = when (contactItems[index].second) {
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
                delay(1100L) // Adjusted delay for the last item
                itemVisible = true
            }
            AnimatedVisibility(
                visible = itemVisible,
                enter = fadeIn(tween(500)) + slideInVertically(tween(500), initialOffsetY = { it / 2 })
            ) {
                val buttonGradient = Brush.horizontalGradient(
                    colors = listOf(DarkPink, LightPink, White)
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
fun ContactInfoItem(icon: Any, label: String, value: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.98f else 1f, label = "")

    val cardGradient = Brush.horizontalGradient(
        colors = listOf(Color.DarkGray.copy(alpha = 0.4f), Color.DarkGray.copy(alpha = 0.2f))
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
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
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
    ) {
        Box(modifier = Modifier.background(brush = cardGradient)) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconModifier = Modifier.size(24.dp)
                when (icon) {
                    is ImageVector -> Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = iconModifier)
                    is Int -> Icon(painter = painterResource(id = icon), contentDescription = label, tint = Color.White, modifier = iconModifier)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = label, color = Color.Gray, fontSize = 12.sp)
                    Text(text = value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

