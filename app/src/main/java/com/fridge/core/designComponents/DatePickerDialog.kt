@file:OptIn(ExperimentalMaterial3Api::class)

package com.fridge.core.designComponents

import java.time.LocalDate
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fridge.R
import com.fridge.core.data.toLocalDate
import com.fridge.core.data.toMillis

@Composable
fun MyDatePickerDialog(
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    selectable: (utcMillis: Long) -> Boolean = { true },
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate.toMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return selectable(utcTimeMillis)
//                utcTimeMillis <= System.currentTimeMillis()
            }
        })

    val selectedDate = datePickerState.selectedDateMillis?.toLocalDate() ?: LocalDate.now()

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text(text = stringResource(R.string.dismiss))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}
