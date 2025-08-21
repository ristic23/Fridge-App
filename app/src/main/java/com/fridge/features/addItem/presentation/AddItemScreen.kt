@file:OptIn(ExperimentalMaterial3Api::class)

package com.fridge.features.addItem.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fridge.R
import com.fridge.core.data.getDayMonthYear
import com.fridge.core.data.getDayMonthYearHourMinutes
import com.fridge.core.data.toMillis
import com.fridge.core.designComponents.MyDatePickerDialog
import com.fridge.core.designComponents.SectionItem
import com.fridge.core.designComponents.TextCheckBox
import com.fridge.core.designComponents.TimePickerDialog
import com.fridge.core.domain.FridgeItem
import com.fridge.ui.theme.FridgeAppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun AddItemWrapper(
    viewModel: AddItemViewModel = hiltViewModel(),
    id: Int?,
    onCancel: () -> Unit,
) {
    val editItem by viewModel.editItem.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        id?.let {
            viewModel.fetchFridgeItem(it)
        }
    }

    AddItemScreen(
        item = editItem,
        isEditMode = id != null,
        onCancel = onCancel,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.saveItem(
                saveFinished = {
                    onCancel()
                }
            )
        }
    )

}

@Composable
fun AddItemScreen(
    item: FridgeItem,
    isEditMode: Boolean,
    onAction: (EditItemActions) -> Unit,
    onCancel: () -> Unit,
    onSaveClick: () -> Unit,
) {
    var showStoredDatePicker by rememberSaveable {
        mutableStateOf(false)
    }
    var showExpiredDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    var showTimePicker by rememberSaveable {
        mutableStateOf(false)
    }
    var pickedTime by rememberSaveable {
        mutableStateOf(Pair(0, 0))
    }

    var isSaveFailed by rememberSaveable {
        mutableStateOf(false)
    }

    fun dismissCorrectDatePicker() {
        if (showStoredDatePicker) {
            showStoredDatePicker = false
        } else {
            showExpiredDatePicker = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onCancel()
                    },
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(
                    id =
                        if (isEditMode) {
                            R.string.edit
                        } else {
                            R.string.create
                        }
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        isSaveFailed = true
                        onSaveClick()
                    },
                painter = painterResource(R.drawable.ic_save),
                contentDescription = "Save",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
        ) {

            InputSectionItem(
                modifier = Modifier
                    .fillMaxWidth(),
                sectionTitle = stringResource(R.string.name),
                icon = painterResource(R.drawable.ic_fridge),
                singleLine = true,
                isError = isSaveFailed && item.name.isBlank(),
                text = item.name,
                hint = stringResource(R.string.nameHint),
                onValueChanged = {
                    onAction(EditItemActions.Name(name = it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            InputSectionItem(
                modifier = Modifier
                    .fillMaxWidth(),
                sectionTitle = stringResource(R.string.note),
                icon = painterResource(R.drawable.ic_note),
                singleLine = false,
                maxLines = 5,
                text = item.note,
                hint = stringResource(R.string.noteHint),
                onValueChanged = {
                    onAction(EditItemActions.Note(note = it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            SectionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showTimePicker = true
                    },
                sectionTitle = stringResource(R.string.timeOfStorage),
                icon = painterResource(R.drawable.ic_time_stored),
                text = item.timeStored.getDayMonthYearHourMinutes()
            )

            Spacer(modifier = Modifier.height(8.dp))
            SectionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showExpiredDatePicker = true
                    },
                isError = isSaveFailed && item.expiredDate == LocalDate.MAX,
                sectionTitle = stringResource(R.string.expiredDate),
                icon = painterResource(R.drawable.ic_expire_date),
                text = item.expiredDate.getDayMonthYear()
            )

            Spacer(modifier = Modifier.height(8.dp))
            InputSectionItem(
                modifier = Modifier
                    .fillMaxWidth(),
                sectionTitle = stringResource(R.string.category),
                icon = painterResource(R.drawable.ic_category),
                singleLine = true,
                text = item.category,
                isError = isSaveFailed && item.category.isBlank(),
                hint = stringResource(R.string.categoryHint),
                onValueChanged = {
                    onAction(EditItemActions.Category(category = it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextCheckBox(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.isProductOpened),
                checked = item.isOpen,
                onCheckedChange = {
                    onAction(EditItemActions.IsOpen(isOpen = it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    when {
        showStoredDatePicker || showExpiredDatePicker -> {
            MyDatePickerDialog(
                currentDate = if (showStoredDatePicker) {
                    item.timeStored.atZone(ZoneId.systemDefault()).toLocalDate()
                } else {
                    item.expiredDate
                },
                selectable = {
                    if (showStoredDatePicker) {
                        it <= LocalDate.now().plusDays(1).toMillis()
                    } else {
                        true
                    }
                },
                onDateSelected = {
                    if (showStoredDatePicker) {
                        val newTime = LocalDateTime.of(
                            it,
                            LocalTime.of(pickedTime.first, pickedTime.second)
                        )
                        val newZone = ZonedDateTime.of(newTime, ZoneId.systemDefault())
                        onAction(EditItemActions.TimeStored(newZone.toInstant()))
                    } else {
                        onAction(EditItemActions.ExpiredDate(expiredDate = it))
                    }
                    dismissCorrectDatePicker()
                },
                onDismiss = {
                    dismissCorrectDatePicker()
                }
            )
        }

        showTimePicker -> {
            TimePickerDialog(
                currentTime = item.timeStored,
                onConfirm = {
                    pickedTime = Pair(it.hour, it.minute)
                    showTimePicker = false
                    showStoredDatePicker = true
                },
                onDismiss = {
                    showTimePicker = false
                },
            )
        }
    }

}

@Composable
fun InputSectionItem(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    icon: Painter,
    maxLines: Int = 1,
    singleLine: Boolean,
    isError: Boolean = false,
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    text: String?,
    hint: String,
    onValueChanged: (String) -> Unit,
    ) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            value = text ?: "",
            onValueChange = {
                onValueChanged(it)
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = icon,
                    contentDescription = sectionTitle,
                    tint = if (isError)
                        MaterialTheme.colorScheme.error
                    else
                        iconTint
                )
            },
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
    }
}

@Preview
@Composable
private fun AddItemScreenPreview() {
    FridgeAppTheme {
        val item = FridgeItem(
            id = 1,
            name = "",
            category = "Protein",
            isOpen = false,
            note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
            expiredDate = LocalDate.now().plusDays(5),
            timeStored = Instant.now(),
        )
        AddItemScreen(
            item = item,
            isEditMode = false,
            onCancel = {},
            onAction = {},
            onSaveClick = {},
        )
    }
}