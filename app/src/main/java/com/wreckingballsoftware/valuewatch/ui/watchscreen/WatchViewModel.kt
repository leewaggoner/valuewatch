package com.wreckingballsoftware.valuewatch.ui.watchscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.valuewatch.ui.watchscreen.models.WatchState

class WatchViewModel(
    handle: SavedStateHandle,
): ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(WatchState())
    }
}
