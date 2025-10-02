package com.example.my_portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 1. Data model for a Milestone
data class Milestone(
    val title: String,
    val description: String,
    val icon: ImageVector
)

// 2. Your milestones and leadership data
val milestoneList = listOf(
    Milestone(
        title = "RedHat Certified System Administrator (RHCSA)",
        description = "Demonstrated expertise in core Linux system administration tasks and competencies.",
        icon = Icons.Outlined.Verified
    ),
    Milestone(
        title = "GirlScript Summer of Code 2024",
        description = "Selected and actively contributed to production-grade open-source projects.",
        icon = Icons.Outlined.Code
    ),
    Milestone(
        title = "300+ DSA Problems Solved",
        description = "Proficient in data structures and algorithms, with extensive practice on LeetCode and GeeksforGeeks.",
        icon = Icons.Outlined.Computer
    ),
    Milestone(
        title = "MongoDB University Certification",
        description = "Certified in core MongoDB concepts, including data modeling, aggregation, and indexing.",
        icon = Icons.Outlined.School
    ),
    Milestone(
        title = "Team Lead at AceHack 4.0 Hackathon",
        description = "Led a team during a national hackathon, managed task distribution, and ensured timely project delivery.",
        icon = Icons.Outlined.Groups
    )
)

// 3. The Main Screen Composable
@Composable
fun MilestonesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Achievements & Leadership",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(milestoneList) { milestone ->
            MilestoneCard(milestone = milestone)
        }
    }
}

// 4. A Single Milestone Card UI
@Composable
fun MilestoneCard(milestone: Milestone) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = milestone.icon,
                contentDescription = "${milestone.title} Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = milestone.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = milestone.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// 5. Preview for Android Studio
@Preview(showBackground = true)
@Composable
fun MilestonesScreenPreview() {
    // Wrap with your app's theme for accurate preview
    // YourPortfolioTheme { MilestonesScreen() }
    MilestonesScreen()
}