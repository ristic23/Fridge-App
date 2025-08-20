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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.fridge.core.designComponents.SectionItem
import com.fridge.core.designComponents.TextCheckBox
import com.fridge.core.domain.FridgeItem
import com.fridge.ui.theme.FridgeAppTheme
import java.time.Instant

@Composable
fun AddItemWrapper(
    viewModel: AddItemViewModel = hiltViewModel(),
    id: Int?,
    onCancel: () -> Unit,
) {
    val editItem by viewModel.editItem.collectAsStateWithLifecycle()


    AddItemScreen(
        item = editItem,
        isEditMode = id != null,
        onCancel = onCancel,
        onSaveClick = {

        }
    )

}

@Composable
fun AddItemScreen(
    item: FridgeItem?,
    isEditMode: Boolean,
    onCancel: () -> Unit,
    onSaveClick: () -> Unit,
) {
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
                text = item?.name,
                hint = stringResource(R.string.nameHint),
                onValueChanged = {
                    // todo lambda action with types and new values
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            InputSectionItem(
                modifier = Modifier
                    .fillMaxWidth(),
                sectionTitle = stringResource(R.string.note),
                icon = painterResource(R.drawable.ic_note),
                singleLine = true,
                text = item?.note,
                hint = stringResource(R.string.noteHint),
                onValueChanged = {
                    // todo lambda action with types and new values
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            SectionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // todo lambda action show time and date picker
                    },
                sectionTitle = stringResource(R.string.timeOfStorage),
                icon = painterResource(R.drawable.ic_time_stored),
                text = (item?.timeStored ?: Instant.now()).getDayMonthYearHourMinutes()
            )

            Spacer(modifier = Modifier.height(8.dp))
            SectionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // todo lambda action show date picker
                    },
                sectionTitle = stringResource(R.string.expiredDate),
                icon = painterResource(R.drawable.ic_expire_date),
                text = item?.expiredDate?.getDayMonthYear() ?: ""
            )

            Spacer(modifier = Modifier.height(8.dp))
            InputSectionItem(
                modifier = Modifier
                    .fillMaxWidth(),
                sectionTitle = stringResource(R.string.category),
                icon = painterResource(R.drawable.ic_category),
                singleLine = true,
                text = item?.category,
                hint = stringResource(R.string.categoryHint),
                onValueChanged = {
                    // todo lambda action with types and new values
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextCheckBox(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.isProductOpened),
                checked = item?.isOpen == true,
                onCheckedChange = {
                    // todo lambda action with types and new values
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun InputSectionItem(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    icon: Painter,
    singleLine: Boolean,
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
            singleLine = singleLine,
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
                    tint = iconTint
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
        val item = null
//        FridgeItem(
//                id = 1,
//                name = "",
//                category = "Protein",
//                isOpen = false,
//                note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
//                expiredDate = LocalDate.now().plusDays(5),
//                timeStored = Instant.now(),
//            )
        AddItemScreen(
            item = item,
            isEditMode = item != null,
            onCancel = {},
            onSaveClick = {},
        )
    }
}