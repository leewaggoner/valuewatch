package com.wreckingballsoftware.valuewatch.ui.preferencesscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import com.wreckingballsoftware.valuewatch.ui.compose.BackgroundColorGrid
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
    fontColor: Color,
    viewModel: PreferencesViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            PreferencesNavigation.GoToWatchScreen -> navGraph.navigateToWatchScreen()
            PreferencesNavigation.GoBack -> navGraph.popBackStack()
        }
    }

    BackHandler {
        viewModel.handleEvent(PreferencesEvent.OnBackClicked)
    }

    PreferencesScreenContent(
        state = viewModel.state,
        fontColor = fontColor,
        handleEvent = viewModel::handleEvent,
    )
}

@Composable
fun PreferencesScreenContent(
    state: PreferencesState,
    fontColor: Color,
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
                .weight(1f)
                .padding(all = MaterialTheme.dimensions.spaceSmall)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.currency_title),
                style = MaterialTheme.valueWatchTypography.title,
                color = fontColor,
            )
            CurrencyDropdown(state = state, fontColor = fontColor, handleEvent = handleEvent)
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            Text(
                text = stringResource(id = R.string.hourly_rate_title),
                style = MaterialTheme.valueWatchTypography.title,
                color = fontColor,
            )
            RateTextField(state = state, fontColor = fontColor, handleEvent = handleEvent)
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            Text(
                text = stringResource(id = R.string.background_color_title),
                style = MaterialTheme.valueWatchTypography.title,
                color = fontColor,
            )
            BackgroundColorGrid(state = state, fontColor = fontColor, handleEvent = handleEvent)
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
        state = PreferencesState(
            bgColors = listOf(
                BackgroundColor("White", 0xFFFFFFFF, 0xFF000000),
                BackgroundColor("Black", 0xFF000000, 0xFFFFFFFF),
                BackgroundColor("Pink", 0xFFFF80ED, 0xFF000000),
                BackgroundColor("Forest", 0xFF065535, 0xFFFFFFFF),
                BackgroundColor("Sand", 0xFFFFA500, 0xFF000000),
                BackgroundColor("Teal", 0xFF40E0D0, 0xFF000000),
                BackgroundColor("Peach", 0xFFFA8072, 0xFF000000),
                BackgroundColor("Red", 0xFF800000, 0xFFFFFFFF),
                BackgroundColor("Violet", 0xFF8A2BE2, 0xFFFFFFFF),
            )
        ),
        fontColor = Color.Black,
        handleEvent = { },
    )
}
