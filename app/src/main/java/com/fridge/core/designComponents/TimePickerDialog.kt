@file:OptIn(ExperimentalMaterial3Api::class)

package com.fridge.core.designComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fridge.R
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun TimePickerDialog(
    currentTime: Instant?,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val zonedDateTime: ZonedDateTime? = currentTime?.atZone(ZoneId.systemDefault())
    val timePickerState = rememberTimePickerState(
        initialHour = zonedDateTime?.hour ?: 12,
        initialMinute = zonedDateTime?.minute ?: 0,
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(timePickerState) }) {
                Text(stringResource(R.string.ok))
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
            )
        }
    )
}
