package com.wreckingballsoftware.valuewatch.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.valuewatch.data.models.BackgroundColor
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesEvent
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesState
import com.wreckingballsoftware.valuewatch.ui.theme.valueWatchTypography

@Composable
fun BackgroundColorGrid(state: PreferencesState, handleEvent: (PreferencesEvent) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.height(300.dp),
        columns = GridCells.Fixed(3),
        ) {
        items(state.bgColors.size) { index ->
            val backgroundColor = state.bgColors[index].backgroundColor
            val textColor = state.bgColors[index].textColor
            val text = state.bgColors[index].colorText
            val isSelected = index == state.selectedBgColor
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
                    .clickable { handleEvent(PreferencesEvent.BackgroundColorChanged(index)) },
                border = BorderStroke(
                    width = 2.dp,
                    color = if (isSelected) Color.Black else Color.LightGray
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(backgroundColor)),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.valueWatchTypography.title,
                        color = Color(textColor),
                    )
                }
            }
        }
    }
}

@Preview(name = "BackgroundColorGrid Preview", showBackground = true)
@Composable
fun BackgroundColorGridPreview() {
    BackgroundColorGrid(
        state = PreferencesState(
            bgColors = listOf(
                BackgroundColor("White", 0xFFFFFFFF.toInt(), 0xFF000000.toInt()),
                BackgroundColor("Black", 0xFF000000.toInt(), 0xFFFFFFFF.toInt()),
                BackgroundColor("Red", 0xFFFF0000.toInt(), 0xFFFFFFFF.toInt()),
                BackgroundColor("Green", 0xFF00FF00.toInt(), 0xFFFFFFFF.toInt()),
                BackgroundColor("Blue", 0xFF0000FF.toInt(), 0xFFFFFFFF.toInt()),
                BackgroundColor("Yellow", 0xFFFFFF00.toInt(), 0xFF000000.toInt()),
                BackgroundColor("Magenta", 0xFFFF00FF.toInt(), 0xFFFFFFFF.toInt()),
                BackgroundColor("Cyan", 0xFF00FFFF.toInt(), 0xFF000000.toInt()),
                BackgroundColor("Gray", 0xFF888888.toInt(), 0xFF000000.toInt()),
            )
        ),
        handleEvent = { }
    )
}