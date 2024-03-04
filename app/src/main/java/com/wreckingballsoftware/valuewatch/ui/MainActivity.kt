package com.wreckingballsoftware.valuewatch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wreckingballsoftware.valuewatch.ui.navigation.ValueWatchHost
import com.wreckingballsoftware.valuewatch.ui.theme.ValueWatchTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val backgroundColor = viewModel.state.backgroundColor
            val skipPreferences = viewModel.state.skipPreferences
            ValueWatchTheme(
                darkTheme = false,
                dynamicColor = false,
            ) {
                ValueWatchHost(
                    skipPreferences = skipPreferences,
                    backgroundColor = backgroundColor,
                )
            }
        }
    }
}
