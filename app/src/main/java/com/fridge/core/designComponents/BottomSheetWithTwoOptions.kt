package com.fridge.core.designComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fridge.R
import com.fridge.ui.theme.FridgeAppTheme

@Composable
fun BottomSheetWithTwoOptions(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                modifier = Modifier,
                onClick = { onDismiss() },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        confirmButton = {
            TextButton(
                modifier = Modifier,
                onClick = { onConfirm() },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        text = {
            Text(
                text = stringResource(id = R.string.areYouSure),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}

@Preview
@Composable
fun BottomSheetWithTwoOptionsPreview() {
    FridgeAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BottomSheetWithTwoOptions(
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}