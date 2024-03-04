package com.wreckingballsoftware.valuewatch.ui.compose

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.valuewatch.R
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesEvent
import com.wreckingballsoftware.valuewatch.ui.preferencesscreen.models.PreferencesState
import com.wreckingballsoftware.valuewatch.utils.CurrencyVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateTextField(
    modifier: Modifier = Modifier,
    state: PreferencesState,
    fontColor: Color,
    handleEvent: (PreferencesEvent) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = state.hourlyRate,
        onValueChange = { rate ->
            handleEvent(PreferencesEvent.HourlyRateChanged(rate))
        },
        label = {
            Text(
                text = stringResource(id = R.string.hourly_rate_label),
                color = fontColor,
            )
        },
        colors = ExposedDropdownMenuDefaults.textFieldColors(),
        visualTransformation = CurrencyVisualTransformation(
            currencySymbol = state.currencySymbol,
            decimalDigits = state.decimalDigits,
            thousandsSymbol = state.thousandsSymbol,
            decimalSymbol = state.decimalSymbol,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                handleEvent(PreferencesEvent.OnDoneClicked)
            }
        ),
    )
}

@Preview(name = "RateTextField Preview", showBackground = true)
@Composable
fun RateTextFieldPreview() {
    RateTextField(
        state = PreferencesState(),
        fontColor = Color.Black,
        handleEvent = { }
    )
}