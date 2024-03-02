package com.wreckingballsoftware.valuewatch.ui.compose

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesEvent
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(
    state: PreferencesState,
    handleEvent: (PreferencesEvent) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = state.dropdownExpanded,
        onExpandedChange = { newValue ->
            handleEvent(PreferencesEvent.ExpandedChanged(expanded = newValue))
        },
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = state.selectedCurrency,
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.currency_label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.dropdownExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = state.dropdownExpanded,
            onDismissRequest = { handleEvent(PreferencesEvent.DismissDropdown) },
        ) {
            state.currencies.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption.currency) },
                    onClick = {
                        handleEvent(
                            PreferencesEvent.CurrencyChanged(
                                currency = selectionOption.abbreviation
                            )
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(name = "CurrencyDropdown", showBackground = true)
@Composable
fun CurrencyDropdownPreview() {
    CurrencyDropdown(
        state = PreferencesState(),
        handleEvent = { }
    )
}
