package com.wreckingballsoftware.valuewatch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            val skipPreferences = currencyRepo.getCurrentHourlyRate().isNotEmpty()

            setContent {
                val backgroundColor = backgroundColorRepo.bgColor.collectAsStateWithLifecycle().value
                ValueWatchTheme(
                    darkTheme = false,
                    dynamicColor = false,
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color(backgroundColor.backgroundColor)
                    ) {
                        ValueWatchHost(skipPreferences = skipPreferences)
                    }
                }
            }
        }
    }
}
