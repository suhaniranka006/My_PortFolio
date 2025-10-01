package com.example.my_portfolio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.my_portfolio.MainScreen
import com.example.my_portfolio.SplashScreen
import com.example.my_portfolio.ui.screens.ExperienceScreen
import com.example.my_portfolio.ui.screens.MilestonesScreen
import com.example.my_portfolio.ui.screens.ProjectsScreen
import com.example.my_portfolio.ui.screens.SkillsScreen

@Composable
fun PortfolioNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        // Splash Screen
        composable("splash_screen") {
            SplashScreen(navController)
        }

        // Main Screen with BottomNavigation
        composable("main_screen") {
            MainScreen(navController) // ✅ Pass NavController here
        }

        // Screens navigated from HomeTab buttons
        composable("projects_screen") { ProjectsScreen() }
        composable("experience_screen") { ExperienceScreen() }
        composable("skills_screen") { SkillsScreen() }
        composable("milestones_screen") { MilestonesScreen() }
    }
}
