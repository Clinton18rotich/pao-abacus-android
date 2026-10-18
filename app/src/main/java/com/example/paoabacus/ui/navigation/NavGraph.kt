package com.example.paoabacus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.paoabacus.ui.screens.*
import com.example.paoabacus.ui.theme.*

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Abacus : Screen("abacus", "Abacus", Icons.Filled.Calculate)
    object Flash : Screen("flash", "Flash", Icons.Filled.Bolt)
    object Operations : Screen("operations", "Math", Icons.Filled.Functions)
    object Rules : Screen("rules", "Rules", Icons.Filled.MenuBook)
    object Timer : Screen("timer", "Timer", Icons.Filled.Timer)
}

@Composable
fun AbacusNavGraph() {
    val navController = rememberNavController()
    val items = listOf(Screen.Abacus, Screen.Flash, Screen.Operations, Screen.Rules, Screen.Timer)

    Scaffold(
        containerColor = DarkBg,
        bottomBar = {
            NavigationBar(containerColor = DarkSurface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label, tint = if (currentRoute == screen.route) Orange else LightGrey) },
                        label = { Text(screen.label, fontSize = 10.sp, color = if (currentRoute == screen.route) Orange else LightGrey) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF2A2A2A))
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = Screen.Abacus.route, modifier = Modifier.padding(padding)) {
            composable(Screen.Abacus.route) { AbacusScreen() }
            composable(Screen.Flash.route) { FlashAnzanScreen() }
            composable(Screen.Operations.route) { OperationsScreen() }
            composable(Screen.Rules.route) { RulesScreen() }
            composable(Screen.Timer.route) { TimerScreen() }
        }
    }
}
