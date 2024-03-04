package com.wreckingballsoftware.valuewatch.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchEvent
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchState

@Composable
fun StartPauseButton(state: WatchState, fontColor: Color, handleEvent: (WatchEvent) -> Unit) {
    IconButton(
        modifier = Modifier
            .size(80.dp)
            .border(
                width = 1.dp,
                color = fontColor,
                shape = CircleShape
            ),
        onClick = {
            handleEvent(WatchEvent.OnPlayButtonClicked)
        }
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp),
            tint = fontColor,
            imageVector = if (state.isTiming) {
                ImageVector.vectorResource(R.drawable.ico_pause)
            } else {
                ImageVector.vectorResource(R.drawable.ico_start)
            },
            contentDescription = stringResource(id = R.string.start)
        )
    }
}

@Preview(name = "StartPauseButton Preview", showBackground = true)
@Composable
fun StartPauseButtonPreview() {
    StartPauseButton(
        state = WatchState(),
        fontColor = Color.Black,
        handleEvent = {}
    )
}
