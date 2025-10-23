package com.suhani.portfolio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhani.portfolio.ui.theme.DarkPink
import com.suhani.portfolio.ui.theme.LightPink
import com.suhani.portfolio.ui.theme.White
import kotlin.math.absoluteValue

// 1. Unified Data Model for Milestones and Education
sealed class AchievementItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector
) {
    class Milestone(title: String, subtitle: String, description: String, icon: ImageVector) :
        AchievementItem(title, subtitle, description, icon)

    class Education(title: String, subtitle: String, description: String, icon: ImageVector) :
        AchievementItem(title, subtitle, description, icon)
}

// 2. Your complete list of achievements and education
val achievementsList = listOf(
    AchievementItem.Milestone(
        title = "RHCSA Certified",
        subtitle = "RedHat Certified System Administrator",
        description = "Demonstrated expertise in core Linux system administration tasks and competencies.",
        icon = Icons.Outlined.Verified
    ),
    AchievementItem.Milestone(
        title = "GSSoC Contributor",
        subtitle = "GirlScript Summer of Code 2024",
        description = "Selected and actively contributed to production-grade open-source projects.",
        icon = Icons.Outlined.Code
    ),
    AchievementItem.Milestone(
        title = "300+ DSA Problems",
        subtitle = "on LeetCode & GeeksforGeeks",
        description = "Proficient in data structures and algorithms, with extensive practice on competitive programming platforms.",
        icon = Icons.Outlined.Computer
    ),
    AchievementItem.Milestone(
        title = "MongoDB Certified",
        subtitle = "MongoDB University",
        description = "Certified in core MongoDB concepts, including data modeling, aggregation, and indexing.",
        icon = Icons.Outlined.Star
    ),
    AchievementItem.Milestone(
        title = "Hackathon Team Lead",
        subtitle = "AceHack 4.0 Hackathon",
        description = "Led a team during a national hackathon, managed task distribution, and ensured timely project delivery.",
        icon = Icons.Outlined.Groups
    ),
    AchievementItem.Education(
        title = "Bachelor of Technology",
        subtitle = "Arya College of Engineering (2022-2026)",
        description = "Information Technology | CGPA: 7.9/10.0",
        icon = Icons.Outlined.School
    ),
    AchievementItem.Education(
        title = "Senior Secondary",
        subtitle = "Govt Sr Secondary School (2021-2022)",
        description = "RBSE | Percentage: 81.40%",
        icon = Icons.Outlined.Book
    ),
    AchievementItem.Education(
        title = "Secondary Education",
        subtitle = "Fateh Academy (2019-2020)",
        description = "RBSE | Percentage: 91.83%",
        icon = Icons.Outlined.Book
    )
)

// 3. The Main Screen Composable
@Composable
fun MilestonesScreen() {
    val pagerState = rememberPagerState(pageCount = { achievementsList.size })
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(White, LightPink, DarkPink)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Achievements & Education",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 64.dp), // Space on the sides to see tilted cards
            pageSpacing = (-16).dp // Negative spacing makes cards overlap slightly
        ) { page ->
            val item = achievementsList[page]
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            AchievementCard(
                item = item,
                modifier = Modifier.graphicsLayer {
                    // Apply the 3D rotation and scaling based on page offset
                    val scaleFactor = 1f - (0.2f * pageOffset.absoluteValue)
                    val rotationY = -30f * pageOffset // Adjust rotation angle here

                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    this.rotationY = rotationY
                    alpha = 1f - (0.5f * pageOffset.absoluteValue)
                }
            )
        }

        PagerIndicator(
            pageCount = achievementsList.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}

// 4. A Single Achievement Card UI
@Composable
fun AchievementCard(item: AchievementItem, modifier: Modifier = Modifier) {
    val iconGradient = Brush.verticalGradient(colors = listOf(LightPink, DarkPink))

    Card(
        modifier = modifier
            .fillMaxHeight(0.75f) // Make cards fill a large portion of the height
            .aspectRatio(0.7f), // Maintain a consistent aspect ratio
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(brush = iconGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = item.subtitle,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

// 5. Reusable Pager Indicator Composable
@Composable
fun PagerIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = currentPage == iteration
            val color by animateColorAsState(
                targetValue = if (isSelected) DarkPink else Color.Gray.copy(alpha = 0.5f),
                animationSpec = tween(300), label = ""
            )
            val size by animateDpAsState(
                targetValue = if (isSelected) 12.dp else 8.dp,
                animationSpec = tween(300), label = ""
            )
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
