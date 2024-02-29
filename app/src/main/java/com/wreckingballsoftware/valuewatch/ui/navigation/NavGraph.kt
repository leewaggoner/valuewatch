package com.wreckingballsoftware.valuewatch.ui.navigation

import androidx.navigation.NavController

class NavGraph(navController: NavController) {
    val navigateToPreferencesScreen: () -> Unit = {
        navController.navigate(
            Destinations.PreferencesScreen
        )
    }
    val navigateToWatchScreen: () -> Unit = {
        navController.navigate(
            Destinations.WatchScreen
        ) {
            //delete stack up to this screen
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}