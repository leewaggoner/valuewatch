package com.wreckingballsoftware.valuewatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.PreferencesScreen
import com.wreckingballsoftware.valuewatch.ui.watchscreen.WatchScreen

@Composable
fun ValueWatchHost() {
    val navController = rememberNavController()
    val navGraph = remember(navController) { NavGraph(navController) }

    val startDestination = Destinations.PreferencesScreen

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Destinations.PreferencesScreen) {
            PreferencesScreen(navGraph = navGraph)
        }

        composable(route = Destinations.WatchScreen) {
            WatchScreen(navGraph = navGraph)
        }
    }
}