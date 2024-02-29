package com.wreckingballsoftware.valuewatch.ui.watchscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.valuewatch.ui.navigation.NavGraph

@Composable
fun WatchScreen(
    navGraph: NavGraph
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Watch Screen",
            )
        }
        Button(
            onClick = {
                navGraph.navigateToPreferencesScreen()
            }
        ) {
            Text(
                text = "Preferences",
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}