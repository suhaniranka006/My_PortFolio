package com.example.my_portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Step 1: Define the data structure right here in the same file.
data class Update(
    val date: String,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun UpdatesTab() {
    // Step 2: Hardcode your list of updates directly inside the composable.
    val updates = listOf(
        Update(
            date = "OCT 2025",
            title = "Launched My Portfolio App",
            description = "Published my personal portfolio to showcase my skills in modern Android development, including Jetpack Compose, Ktor, and Coroutines.",
            icon = Icons.Default.RocketLaunch
        ),
        Update(
            date = "JUL 2025",
            title = "Mastered Ktor Networking",
            description = "Deep dived into Ktor, building a service layer to consume live APIs and remote JSON data sources.",
            icon = Icons.Default.Code
        ),
        Update(
            date = "JAN 2025",
            title = "First App Published",
            description = "Successfully developed and published my first application, 'Cinephile', to the app store, learning about the full development lifecycle.",
            icon = Icons.Default.EmojiEvents
        ),
        Update(
            date = "MAY 2024",
            title = "Graduated University",
            description = "Completed my Bachelor of Technology in Computer Science, finishing with a focus on mobile application development.",
            icon = Icons.Default.School
        )
    )

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF1A1A1A))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 32.dp)
    ) {
        item {
            Text(
                text = "My Journey",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        itemsIndexed(updates) { index, update ->
            var itemVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(100L + (index * 150L)) // Staggered delay
                itemVisible = true
            }

            AnimatedVisibility(
                visible = itemVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(durationMillis = 500)
                        )
            ) {
                TimelineItem(update = update, isLastItem = index == updates.lastIndex)
            }
        }
    }
}

/**
 * A reusable composable for an item in the vertical timeline.
 */
@Composable
fun TimelineItem(update: Update, isLastItem: Boolean) {
    Row {
        // This Column holds the line and the dot for the timeline visual
        Column(
            modifier = Modifier.width(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = update.icon,
                    contentDescription = "Event Icon",
                    modifier = Modifier.size(12.dp),
                    tint = Color.White
                )
            }
            if (!isLastItem) {
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f) // Fills the remaining space
                        .background(Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // This Column holds the actual content
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                text = update.date,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = update.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = update.description,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}