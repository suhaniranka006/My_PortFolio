package com.example.my_portfolio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
import com.example.my_portfolio.ui.theme.DarkPink
import com.example.my_portfolio.ui.theme.LightPink
import com.example.my_portfolio.ui.theme.White
import kotlinx.coroutines.delay

// 1. Data model for a skill
data class Skill(
    val name: String,
    val icon: ImageVector
)

// 2. Your skills, categorized and populated with new data
val languages = listOf(
    Skill("C", Icons.Outlined.Code),
    Skill("C++", Icons.Outlined.Code),
    Skill("JavaScript", Icons.Outlined.Javascript),
    Skill("Java", Icons.Outlined.IntegrationInstructions),
    Skill("Kotlin", Icons.Outlined.DataObject)
)

val webAndMobile = listOf(
    Skill("HTML", Icons.Outlined.Html),
    Skill("CSS", Icons.Outlined.Css),
    Skill("Android Studio", Icons.Outlined.Android),
    Skill("XML Layouts", Icons.Outlined.Terminal),
    Skill("MVVM Arch", Icons.Outlined.Architecture)
)

val csFundamentals = listOf(
    Skill("DSA", Icons.Outlined.AccountTree),
    Skill("OOP", Icons.Outlined.Extension),
    Skill("OS", Icons.Outlined.Dns),
    Skill("Networks", Icons.Outlined.Lan),
    Skill("DBMS", Icons.Outlined.Storage),
    Skill("SQL", Icons.Outlined.DataObject)
)

val toolsAndPlatforms = listOf(
    Skill("Git", Icons.Outlined.Commit),
    Skill("GitHub", Icons.Outlined.Folder),
    Skill("Linux", Icons.Outlined.Terminal),
    Skill("Firebase", Icons.Outlined.Cloud),
    Skill("Google Maps SDK", Icons.Outlined.Map),
    Skill("FCM", Icons.Outlined.Notifications),
    Skill("Room DB", Icons.Outlined.TableView)
)


// 3. The Main Screen Composable with Animations
@Composable
fun SkillsScreen() {
    var animationIndex = 0

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(White, LightPink, DarkPink)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush), // New gradient background
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Languages Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("Languages", Icons.Outlined.Translate)
        }
        items(languages, key = { it.name }) { skill ->
            StaggeredAnimatedItem(index = animationIndex++) {
                SkillCard(skill = skill)
            }
        }

        // --- Web & Mobile Development Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("Web & Mobile Development", Icons.Outlined.PhoneAndroid)
        }
        items(webAndMobile, key = { it.name }) { skill ->
            StaggeredAnimatedItem(index = animationIndex++) {
                SkillCard(skill = skill)
            }
        }

        // --- CS Fundamentals Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("CS Fundamentals", Icons.Outlined.Book)
        }
        items(csFundamentals, key = { it.name }) { skill ->
            StaggeredAnimatedItem(index = animationIndex++) {
                SkillCard(skill = skill)
            }
        }

        // --- Tools & Platforms Section ---
        item(span = { GridItemSpan(maxLineSpan) }) {
            SkillCategoryHeader("Tools & Platforms", Icons.Outlined.Build)
        }
        items(toolsAndPlatforms, key = { it.name }) { skill ->
            StaggeredAnimatedItem(index = animationIndex++) {
                SkillCard(skill = skill)
            }
        }
    }
}

// Wrapper for handling staggered animation logic
@Composable
fun StaggeredAnimatedItem(index: Int, content: @Composable () -> Unit) {
    var itemVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L + (index * 50L)) // Staggered delay
        itemVisible = true
    }
    AnimatedVisibility(
        visible = itemVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) +
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(durationMillis = 300)
                )
    ) {
        content()
    }
}


// 4. UI for a Category Header (Enhanced with an Icon)
@Composable
fun SkillCategoryHeader(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$title Icon",
            tint = DarkPink,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black // Changed to Black for readability
        )
    }
}

// 5. UI for a single Skill Card (New "Frosted Glass" Design)
@Composable
fun SkillCard(skill: Skill, modifier: Modifier = Modifier) {
    val iconGradient = Brush.verticalGradient(
        colors = listOf(LightPink, DarkPink)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        // New semi-transparent white background for "frosted glass" effect
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.7f)),
        border = BorderStroke(1.dp, DarkPink.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(brush = iconGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = skill.icon,
                    contentDescription = "${skill.name} Icon",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = skill.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black, // Changed to Black for readability
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

