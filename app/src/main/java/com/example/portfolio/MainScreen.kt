package com.suhani.portfolio


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import com.suhani.portfolio.R
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
import com.suhani.portfolio.ui.theme.Black
import com.suhani.portfolio.ui.theme.DarkPink
import com.suhani.portfolio.ui.theme.LightPink


// Bottom navigation items
sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Default.Home)
    object Updates : BottomNavItem("Updates", Icons.Default.Notifications)
    object Contact : BottomNavItem("Contact", Icons.Default.Person)
}

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkPink,
                modifier = Modifier
                    .padding(0.dp)
                    .clip(RoundedCornerShape(0.dp))
                    .shadow(0.dp)

            ) {
                listOf(BottomNavItem.Home, BottomNavItem.Updates, BottomNavItem.Contact).forEach { item ->
                    val scale by animateFloatAsState(if (selectedItem == item) 1.2f else 1f)
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title, modifier = Modifier.scale(scale)) },
                        label = { Text(item.title) },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White,
                            indicatorColor = Color.Black                       )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(DarkPink, DarkPink)
                    )
                )
        ) {
            when (selectedItem) {
                is BottomNavItem.Home -> HomeTab(navController)
                is BottomNavItem.Updates -> UpdatesTab()
                is BottomNavItem.Contact -> ContactTab()
            }
        }
    }
}








@Composable
fun HomeTab(navController: NavHostController) {
    val buttons = listOf(
        Triple("Projects", Icons.Default.Home, "projects_screen"),
        Triple("Experience", Icons.Default.Work, "experience_screen"),
        Triple("Skills", Icons.Default.Person, "skills_screen"),
        Triple("Milestones", Icons.Default.Notifications, "milestones_screen")
    )

    // Visibility states for staggered animation
    val visibleStates = remember { List(buttons.size) { mutableStateOf(false) } }

    // Animate buttons one by one
    LaunchedEffect(Unit) {
        buttons.forEachIndexed { index, _ ->
            kotlinx.coroutines.delay(300L * index)
            visibleStates[index].value = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.White, DarkPink))),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(DarkPink, LightPink))),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_pic),
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tagline
            Text(
                text = "Android Developer | Jetpack Compose Enthusiast",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(36.dp))




            // Diamond-shaped buttons
            Box(modifier = Modifier.size(290.dp), contentAlignment = Alignment.Center) {

                // Top button
                androidx.compose.animation.AnimatedVisibility(
                    visible = visibleStates[0].value,
                    enter = fadeIn(animationSpec = tween(100)) +
                            scaleIn(animationSpec = tween(100)) +
                            slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(100))
                ) {
                    Box(modifier = Modifier.offset(y = (-110).dp)) {
                        CircularHomeButton(buttons[0].first, buttons[0].second) {
                            navController.navigate(buttons[0].third)
                        }
                    }
                }

                // Left button
                androidx.compose.animation.AnimatedVisibility(
                    visible = visibleStates[1].value,
                    enter = fadeIn(animationSpec = tween(100)) +
                            scaleIn(animationSpec = tween(100)) +
                            slideInHorizontally(initialOffsetX = { it / 2 }, animationSpec = tween(100))
                ) {
                    Box(modifier = Modifier.offset(x = (-110).dp)) {
                        CircularHomeButton(buttons[1].first, buttons[1].second) {
                            navController.navigate(buttons[1].third)
                        }
                    }
                }

                // Right button
                androidx.compose.animation.AnimatedVisibility(
                    visible = visibleStates[2].value,
                    enter = fadeIn(animationSpec = tween(100)) +
                            scaleIn(animationSpec = tween(100)) +
                            slideInHorizontally(initialOffsetX = { -it / 2 }, animationSpec = tween(100))
                ) {
                    Box(modifier = Modifier.offset(x = 110.dp)) {
                        CircularHomeButton(buttons[2].first, buttons[2].second) {
                            navController.navigate(buttons[2].third)
                        }
                    }
                }

                // Bottom button
                androidx.compose.animation.AnimatedVisibility(
                    visible = visibleStates[3].value,
                    enter = fadeIn(animationSpec = tween(100)) +
                            scaleIn(animationSpec = tween(100)) +
                            slideInVertically(initialOffsetY = { -it / 2 }, animationSpec = tween(100))
                ) {
                    Box(modifier = Modifier.offset(y = 110.dp)) {
                        CircularHomeButton(buttons[3].first, buttons[3].second) {
                            navController.navigate(buttons[3].third)
                        }
                    }
                }
            }



        }
    }
}







@Composable
fun CircularHomeButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(listOf(DarkPink, Black))
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(36.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

// fix profile pic circle is good but full picture is not there head is cut , reduce height of navigation bar make it standard , fix and faster animations , make smooth app and make it more attractcie