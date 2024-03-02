package com.wreckingballsoftware.valuewatch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.wreckingballsoftware.valuewatch.data.BackgroundColorRepo
import com.wreckingballsoftware.valuewatch.data.CurrencyRepo
import com.wreckingballsoftware.valuewatch.ui.navigation.ValueWatchHost
import com.wreckingballsoftware.valuewatch.ui.theme.ValueWatchTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val currencyRepo: CurrencyRepo by inject()
    private val backgroundColorRepo: BackgroundColorRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
//            currencyRepo.clear()
            backgroundColorRepo.initialize()
            currencyRepo.initialize()
            val backgroundColor = backgroundColorRepo.getBackgroundColor()
            val skipPreferences = currencyRepo.getCurrentHourlyRate().isNotEmpty()

            setContent {
                ValueWatchTheme(
                    backgroundColor = backgroundColor,
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ValueWatchHost(skipPreferences = skipPreferences)
                    }
                }
            }
        }
    }
}
