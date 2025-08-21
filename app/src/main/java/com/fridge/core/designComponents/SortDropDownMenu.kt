package com.fridge.core.designComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fridge.R

@Composable
fun SortDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onActionFirst: () -> Unit,
    onActionSecond: () -> Unit,
    onActionThird: () -> Unit,
) {
    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.nameSort),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = onActionFirst
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.expiredSort),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = onActionSecond
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.storedSort),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = onActionThird
            )
        }
    }
}