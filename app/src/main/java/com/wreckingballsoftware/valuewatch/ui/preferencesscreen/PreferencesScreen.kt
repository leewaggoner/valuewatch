package com.wreckingballsoftware.valuewatch.ui.preferencesscreen

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.compose.CurrencyDropdown
import com.wreckingballsoftware.valuewatch.ui.compose.RateTextField
import com.wreckingballsoftware.valuewatch.ui.navigation.NavGraph
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesEvent
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesNavigation
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesState
import com.wreckingballsoftware.valuewatch.ui.theme.dimensions
import com.wreckingballsoftware.valuewatch.ui.theme.valueWatchTypography
import org.koin.androidx.compose.getViewModel

@Composable
fun PreferencesScreen(
    navGraph: NavGraph,
    viewModel: PreferencesViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            PreferencesNavigation.GoToWatchScreen -> navGraph.navigateToWatchScreen()
        }
    }

    BackHandler {
        viewModel.handleEvent(PreferencesEvent.OnBackClicked)
        navGraph.popBackStack()
    }

    PreferencesScreenContent(
        state = viewModel.state,
        handleEvent = viewModel::handleEvent,
    )
}

@Composable
fun PreferencesScreenContent(
    state: PreferencesState,
    handleEvent: (PreferencesEvent) -> Unit,
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
                text = stringResource(id = R.string.currency_title),
                style = MaterialTheme.valueWatchTypography.title,
            )
            CurrencyDropdown(state = state, handleEvent = handleEvent)
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            Text(
                text = stringResource(id = R.string.hourly_rate_title),
                style = MaterialTheme.valueWatchTypography.title,
            )
            RateTextField(state = state, handleEvent = handleEvent)
        }
        Button(
            modifier = Modifier.width(MaterialTheme.dimensions.buttonWidth),
            onClick = {
                handleEvent(PreferencesEvent.OnDoneClicked)
            }
        ) {
            Text(
                text = "Done",
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
    }
}

@Preview(name = "PreferencesScreenContent Preview", showBackground = true)
@Composable
fun PreferencesScreenContentPreview() {
    PreferencesScreenContent(
        state = PreferencesState(),
        handleEvent = { },
    )
}
