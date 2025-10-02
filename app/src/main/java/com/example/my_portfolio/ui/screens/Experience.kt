package com.example.my_portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(experienceList) { experience ->
            ExperienceTimelineCard(experience = experience, isLastItem = experience == experienceList.last())
        }
    }
}

// 4. A Single Experience Card with a Timeline element
@Composable
fun ExperienceTimelineCard(experience: ExperienceItem, isLastItem: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Timeline visuals (line and dot)
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            if (!isLastItem) {
                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Experience details
        Column(modifier = Modifier.padding(bottom = if (!isLastItem) 24.dp else 0.dp)) {
            Text(
                text = experience.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${experience.company} | ${experience.dates}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            experience.responsibilities.forEach { responsibility ->
                ResponsibilityText(text = responsibility)
            }
        }
    }
}

// 5. UI for a single responsibility point
@Composable
fun ResponsibilityText(text: String) {
    Row(
        modifier = Modifier.padding(bottom = 6.dp),
        verticalAlignment = androidx.compose.ui.Alignment.Top
    ) {
        Text(
            text = "• ",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 2.dp) // Align bullet with text
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
