package com.example.my_portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_portfolio.ui.theme.Black
import com.example.my_portfolio.ui.theme.DarkPink
import com.example.my_portfolio.ui.theme.LightPink
import com.example.my_portfolio.ui.theme.White
import kotlinx.coroutines.delay

// Data structure for an update
data class Update(
    val date: String,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun UpdatesTab() {
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
            date = "SEP 2024",
            title = "Led a College Project",
            description = "Took the lead role in a major college project, managing tasks and coordinating with team members to build a functional prototype on a tight deadline.",
            icon = Icons.Default.Groups
        ),
        Update(
            date = "MAY 2024",
            title = "Graduated University",
            description = "Completed my Bachelor of Technology in Computer Science, finishing with a focus on mobile application development.",
            icon = Icons.Default.School
        ),
        Update(
            date = "DEC 2023",
            title = "Participated in First Hackathon",
            description = "Collaborated with a team in a 24-hour hackathon, rapidly developing a concept and presenting a working demo.",
            icon = Icons.Default.Lightbulb
        ),
        Update(
            date = "AUG 2023",
            title = "Learned Git & GitHub",
            description = "Mastered version control with Git and GitHub, enabling effective collaboration and project management.",
            icon = Icons.Default.Build
        ),
        Update(
            date = "JAN 2023",
            title = "Started Android Development",
            description = "Began my journey into mobile development, learning the fundamentals of Kotlin and the Android SDK.",
            icon = Icons.Default.PlayArrow
        )
    )

    // State to keep track of which card is expanded. null means no card is expanded.
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(White, LightPink, DarkPink)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 32.dp)
    ) {
        item {
            var headerVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                headerVisible = true
            }

            AnimatedVisibility(
                visible = headerVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 100)) +
                        slideInVertically(
                            initialOffsetY = { -it / 2 },
                            animationSpec = tween(durationMillis = 100)
                        )
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = "Journey Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp).padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Update Timeline",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(Black, Black)
                            ),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                }
            }
        }

        itemsIndexed(updates) { index, update ->
            var itemVisible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                // FASTER: Reduced the stagger delay
                delay(index * 50L)
                itemVisible = true
            }

            AnimatedVisibility(
                visible = itemVisible,
                // FASTER: Reduced the animation duration
                enter = fadeIn(animationSpec = tween(durationMillis = 100)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(durationMillis = 100)
                        )
            ) {
                TimelineItem(
                    update = update,
                    isExpanded = expandedIndex == index,
                    isLastItem = index == updates.lastIndex,
                    onClick = {
                        expandedIndex = if (expandedIndex == index) null else index
                    }
                )
            }
        }
    }
}

@Composable
fun TimelineItem(update: Update, isExpanded: Boolean, isLastItem: Boolean, onClick: () -> Unit) {
    Row {
        // Timeline visual (dot and line)
        Column(
            modifier = Modifier.width(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = update.icon,
                    contentDescription = "Event Icon",
                    modifier = Modifier.size(22.dp),
                    tint = Color.White
                )
            }
            if (!isLastItem) {
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(Color.Gray.copy(alpha = 0.5f))
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Clickable Card that contains the content
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLastItem) 0.dp else 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                // SMOOTHER: Added easing to the color animation
                containerColor = animateColorAsState(
                    if (isExpanded) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.7f),
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "card_color_animation"
                ).value
            )
        ) {
            Column(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(16.dp)
                    .animateContentSize( // SMOOTHER: Added easing for a more natural expansion
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title and Date
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = update.date,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = update.title,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    // Animated Arrow Icon
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (isExpanded) 90f else 0f,
                        // SMOOTHER: Added easing to the arrow rotation
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                        label = "arrow_rotation"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Expand",
                        modifier = Modifier.rotate(rotationAngle)
                    )
                }

                // Conditionally show the description when expanded
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = update.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

