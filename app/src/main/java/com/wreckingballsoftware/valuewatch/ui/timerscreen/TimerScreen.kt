package com.wreckingballsoftware.valuewatch.ui.timerscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.valuewatch.ui.compose.ResetButton
import com.wreckingballsoftware.valuewatch.ui.compose.StartPauseButton
import com.wreckingballsoftware.valuewatch.ui.navigation.NavGraph
import com.wreckingballsoftware.valuewatch.ui.theme.Black
import com.wreckingballsoftware.valuewatch.ui.theme.dimensions
import com.wreckingballsoftware.valuewatch.ui.theme.valueWatchTypography
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerEvent
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerNavigation
import com.wreckingballsoftware.valuewatch.ui.timerscreen.models.TimerState
import org.koin.androidx.compose.getViewModel

@Composable
fun TimerScreen(
    navGraph: NavGraph,
    fontColor: Color,
    viewModel: TimerViewModel = getViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(TimerEvent.Initialize)
    }

    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            TimerNavigation.GoToPreferences -> navGraph.navigateToPreferencesScreen()
        }
    }

    TimerScreenContent(
        state = viewModel.state,
        fontColor = fontColor,
        handleEvent = viewModel::handleEvent,
    )
}

@Composable
fun TimerScreenContent(
    state: TimerState,
    fontColor: Color,
    handleEvent: (TimerEvent) -> Unit,
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
                text = state.time,
                style = MaterialTheme.valueWatchTypography.headline,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            Text(
                text = "${state.currencySymbol}${state.money}",
                style = MaterialTheme.valueWatchTypography.moneyText,
                color = fontColor
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            StartPauseButton(state = state, fontColor = fontColor, handleEvent = handleEvent)
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceLarge))
            ResetButton(fontColor = fontColor, handleEvent = handleEvent)
        }
        Button(
            modifier = Modifier.width(MaterialTheme.dimensions.buttonWidth),
            onClick = {
                handleEvent(TimerEvent.OnPreferencesButtonClicked)
            }
        ) {
            Text(
                text = "Preferences",
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
    }
}

@Preview(name = "TimerScreenContent Preview", showBackground = true)
@Composable
fun TimerScreenContentPreview() {
    TimerScreenContent(
        state = TimerState(),
        fontColor = Black,
        handleEvent = { }
    )
}