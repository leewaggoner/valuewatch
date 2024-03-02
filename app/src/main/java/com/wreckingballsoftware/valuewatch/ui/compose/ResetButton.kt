package com.wreckingballsoftware.valuewatch.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.theme.dimensions
import com.wreckingballsoftware.valuewatch.ui.theme.valueWatchTypography
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchEvent

@Composable
fun ResetButton(handleEvent: (WatchEvent) -> Unit) {
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

@Preview(name = "ResetButton Preview", showBackground = true)
@Composable
fun ResetButtonPreview() {
    ResetButton(handleEvent = { })
}