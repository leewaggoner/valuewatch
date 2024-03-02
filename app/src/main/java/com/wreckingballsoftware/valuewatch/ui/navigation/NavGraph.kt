package com.wreckingballsoftware.valuewatch.ui.navigation

import androidx.navigation.NavController

class NavGraph(navController: NavController) {
    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }
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