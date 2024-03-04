package com.wreckingballsoftware.valuewatch.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.PreferencesScreen
import com.wreckingballsoftware.valuewatch.ui.watchscreen.WatchScreen

@Composable
fun ValueWatchHost(
    skipPreferences: Boolean,
    backgroundColor: BackgroundColor,
) {
    val navController = rememberNavController()
    val navGraph = remember(navController) { NavGraph(navController) }

    var startDestination = Destinations.PreferencesScreen
    if (skipPreferences) {
        startDestination = Destinations.WatchScreen
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Destinations.PreferencesScreen) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color(backgroundColor.backgroundColor)
            ) {
                PreferencesScreen(
                    navGraph = navGraph,
                    fontColor = Color(backgroundColor.textColor)
                )
            }
        }

        composable(route = Destinations.WatchScreen) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color(backgroundColor.backgroundColor)
            ) {
                WatchScreen(
                    navGraph = navGraph,
                    fontColor = Color(backgroundColor.textColor)
                )
            }
        }
    }
}