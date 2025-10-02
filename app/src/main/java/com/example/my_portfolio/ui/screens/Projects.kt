package com.example.my_portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data Model for a Project
data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>
)

// 2. Your specific project data
val projectList = listOf(
    Project(
        title = "JainConnect",
        description = "A community-focused application to connect members of the Jain community, featuring event updates, a business directory, and matrimonial services. The backend is powered by MongoDB, communicating via a Retrofit-based API.",
        technologies = listOf("MongoDB", "Kotlin", "Retrofit", "Android", "MVVM")
    ),
    Project(
        title = "FoodiHelp",
        description = "A platform that connects restaurants with excess food to local NGOs. It uses Firebase for real-time data synchronization and notifications, all built on a robust MVVM architecture.",
        technologies = listOf("Firebase", "Kotlin", "MVVM", "Android", "Google Maps SDK")
    ),
    Project(
        title = "My Portfolio App",
        description = "A personal portfolio application built with modern practices to showcase my skills, projects, and experience in a clean, interactive, and fully native format.",
        technologies = listOf("Kotlin", "Jetpack Compose", "Android", "Material Design 3")
    )
)

// 3. The Main Screen Composable
@Composable
fun ProjectsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(projectList) { project ->
            ProjectCard(project = project)
        }
    }
}

// 4. A Single Project Card UI - THIS IS THE STABLE VERSION
@Composable
fun ProjectCard(project: Project) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Project Title
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Project Description
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Technologies Used (in a horizontally scrolling row)
            // This LazyRow is a stable alternative to the crashing FlowRow
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(project.technologies) { techName ->
                    TechnologyChip(name = techName)
                }
            }
        }
    }
}

// 5. UI for a single Technology Chip
@Composable
fun TechnologyChip(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp) // Added a bit more padding
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
