package com.example.my_portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data model for a skill
data class Skill(
    val name: String,
    val description: String,
    val icon: ImageVector
)

// 2. Your skills, categorized and populated with real data
val mobileSkills = listOf(
    Skill("Android Dev", "Native Android app development.", Icons.Outlined.Android),
    Skill("Kotlin", "Primary language for building modern, robust apps.", Icons.Outlined.Code),
    Skill("Jetpack", "Suite of libraries for high-quality app development.", Icons.Outlined.Extension),
    Skill("XML", "Traditional UI design and layout for Android.", Icons.Outlined.DataObject)
)

val backendAndDatabaseSkills = listOf(
    Skill("Room DB", "Robust SQL object mapping for offline data.", Icons.Outlined.Storage),
    Skill("Retrofit", "Type-safe HTTP client for Android and Java.", Icons.Outlined.Http),
    Skill("MySQL", "Relational database for structured data.", Icons.Outlined.AccountTree),
    Skill("MongoDB", "NoSQL database for flexible, scalable data.", Icons.Outlined.Cloud)
)

val architectureAndTools = listOf(
    Skill("MVVM", "Modern architecture for scalable apps.", Icons.Outlined.Architecture),
    Skill("Linux", "Server management and deployment environment.", Icons.Outlined.Terminal)
)


// 3. The Main Screen Composable
@Composable
fun SkillsScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Defines a grid with 2 columns
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Mobile Development Section ---
        item(span = { GridItemSpan(maxLineSpan) }) { // Make the header span both columns
            SkillCategoryHeader("Mobile Development")
        }
        items(mobileSkills) { skill ->
            SkillCard(skill = skill)
        }

        // --- Backend & Databases Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("Backend & Databases")
        }
        items(backendAndDatabaseSkills) { skill ->
            SkillCard(skill = skill)
        }

        // --- Architecture & Tools Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("Architecture & Tools")
        }
        items(architectureAndTools) { skill ->
            SkillCard(skill = skill)
        }
    }
}

// 4. UI for a Category Header
@Composable
fun SkillCategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp, top = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

// 5. UI for a single Skill Card
@Composable
fun SkillCard(skill: Skill, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = skill.icon,
                contentDescription = "${skill.name} Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = skill.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = skill.description,
                fontSize = 12.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.defaultMinSize(minHeight = 40.dp)
            )
        }
    }
}