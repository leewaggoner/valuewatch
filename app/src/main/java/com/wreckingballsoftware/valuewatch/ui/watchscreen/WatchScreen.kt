package com.wreckingballsoftware.valuewatch.ui.watchscreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.navigation.NavGraph
import com.wreckingballsoftware.valuewatch.ui.theme.dimensions
import com.wreckingballsoftware.valuewatch.ui.theme.valueWatchTypography
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchEvent
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchNavigation
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchState
import org.koin.androidx.compose.getViewModel

@Composable
fun WatchScreen(
    navGraph: NavGraph,
    viewModel: WatchViewModel = getViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            WatchNavigation.GoToPreferences -> navGraph.navigateToPreferencesScreen()
        }
    }

    WatchScreenContent(
        state = viewModel.state,
        handleEvent = viewModel::handleEvent,
    )
}

@Composable
private fun WatchScreenContent(
    state: WatchState,
    handleEvent: (WatchEvent) -> Unit,
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
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            Text(
                text = "${state.currencySymbol}${state.money}",
                style = MaterialTheme.valueWatchTypography.moneyText,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
            IconButton(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    ),
                onClick = {
                    handleEvent(WatchEvent.OnPlayButtonClicked)
                }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = if (state.isTiming) {
                        ImageVector.vectorResource(R.drawable.ico_pause)
                    } else {
                        ImageVector.vectorResource(R.drawable.ico_start)
                    },
                    contentDescription = stringResource(id = R.string.start)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceLarge))
            Button(
                modifier = Modifier
                    .width(MaterialTheme.dimensions.buttonWidth)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RectangleShape
                    ),
                onClick = { handleEvent(WatchEvent.OnResetButtonClicked) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.reset),
                    color = Color.Black,
                    style = MaterialTheme.valueWatchTypography.title
                )
            }
        }
        Button(
            modifier = Modifier.width(MaterialTheme.dimensions.buttonWidth),
            onClick = {
                handleEvent(WatchEvent.OnPreferencesButtonClicked)
            }
        ) {
            Text(
                text = "Preferences",
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.spaceSmall))
    }
}

@Preview(name = "WatchScreenContent Preview", showBackground = true)
@Composable
fun WatchScreenContentPreview() {
    WatchScreenContent(
        state = WatchState(),
        handleEvent = { },
    )
}
