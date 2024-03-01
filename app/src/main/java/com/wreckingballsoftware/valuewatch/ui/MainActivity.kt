package com.wreckingballsoftware.valuewatch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.wreckingballsoftware.valuewatch.data.DataStoreWrapper
import com.wreckingballsoftware.valuewatch.ui.navigation.ValueWatchHost
import com.wreckingballsoftware.valuewatch.ui.theme.ValueWatchTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val dataStoreWrapper: DataStoreWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val skipPreferences = dataStoreWrapper.getCurrency("").isNotEmpty()
            setContent {
                ValueWatchTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ValueWatchHost(skipPreferences = skipPreferences)
                    }
                }
            }
        }
    }
}
