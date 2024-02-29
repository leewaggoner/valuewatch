package com.wreckingballsoftware.valuewatch.ui.preferencesscreen

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
fun PreferencesScreen(
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
                text = "Preferences Screen",
            )
        }
        Button(
            onClick = {
                navGraph.navigateToWatchScreen()
            }
        ) {
            Text(
                text = "Done",
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
