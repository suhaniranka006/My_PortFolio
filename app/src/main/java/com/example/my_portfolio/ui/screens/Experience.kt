package com.example.my_portfolio.ui.screens

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WorkHistory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_portfolio.ui.theme.DarkPink
import com.example.my_portfolio.ui.theme.LightPink
import com.example.my_portfolio.ui.theme.White
import kotlinx.coroutines.delay

// 1. Data model for an experience item
data class ExperienceItem(
    val company: String,
    val dates: String,
    val title: String,
    val responsibilities: List<String>
)

// 2. Your experience data
val experienceList = listOf(
    ExperienceItem(
        company = "Shiv Gauri Granites Pvt. Ltd.",
        dates = "May 2025 – July 2025",
        title = "Software Developer Intern",
        responsibilities = listOf(
            "Built an offline attendance app using Kotlin and Room DB to track and store daily records for 30+ employees.",
            "Optimized local data access via SQLite, reducing read/write latency by 40% in low-connectivity settings."
        )
    ),
    ExperienceItem(
        company = "WaySpire (Tryst, IIT Delhi)",
        dates = "July 2024 – August 2024",
        title = "Android Development Trainee",
        responsibilities = listOf(
            "Developed 3+ Android apps with MVVM architecture and XML UIs, incorporating best practices in clean code.",
            "Integrated Firebase, Google Maps SDK, and FCM to deliver real-time features and push notification features."
        )
    ),
    ExperienceItem(
        company = "Oasis Infobyte",
        dates = "March 2024 – April 2024",
        title = "Web Development Intern",
        responsibilities = listOf(
            "Designed front-end web pages using HTML, CSS, and JavaScript with responsive layouts and UI effects."
        )
    )
)

// 3. The Main Screen Composable
@Composable
fun ExperienceScreen() {
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(White, LightPink, DarkPink)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.WorkHistory,
                    contentDescription = "Experience Icon",
                    tint = DarkPink,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Work Experience",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        itemsIndexed(experienceList) { index, experience ->
            ExperienceTimelineCard(
                experience = experience,
                isLastItem = index == experienceList.lastIndex,
                animationDelay = (index + 1) * 75L // FASTER: Staggered delay reduced
            )
        }
    }
}

// 4. A Single Experience Card with a Timeline element and 3D Flip Animation
@Composable
fun ExperienceTimelineCard(experience: ExperienceItem, isLastItem: Boolean, animationDelay: Long) {
    // --- 3D Flip-In Animation Logic ---
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(animationDelay)
        isVisible = true
    }

    val rotationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else -90f,
        // FASTER: Animation duration reduced
        animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing),
        label = "flip_rotation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        // FASTER: Animation duration reduced
        animationSpec = tween(durationMillis = 300),
        label = "flip_alpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.rotationY = rotationY
                this.alpha = alpha
            }
    ) {
        // Timeline visuals (line and dot)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(LightPink, DarkPink)
                        )
                    )
            )
            if (!isLastItem) {
                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f),
                    color = DarkPink.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Experience details Card
        Card(
            modifier = Modifier.padding(bottom = if (!isLastItem) 24.dp else 0.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.7f)),
            border = BorderStroke(1.dp, DarkPink.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = experience.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkPink
                )
                Text(
                    text = "${experience.company} | ${experience.dates}",
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
                experience.responsibilities.forEach { responsibility ->
                    ResponsibilityText(text = responsibility)
                }
            }
        }
    }
}

// 5. UI for a single responsibility point
@Composable
fun ResponsibilityText(text: String) {
    Row(
        modifier = Modifier.padding(bottom = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "• ",
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.8f),
        )
    }
}

