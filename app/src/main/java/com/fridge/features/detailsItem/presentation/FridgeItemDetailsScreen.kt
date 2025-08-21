package com.fridge.features.detailsItem.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fridge.R
import com.fridge.core.data.getDayMonthYear
import com.fridge.core.data.getDayMonthYearHourMinutes
import com.fridge.core.designComponents.SectionItem
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Loading
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Error
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Empty
import com.fridge.features.detailsItem.presentation.FridgeItemScreenStates.Success
import com.fridge.ui.theme.FridgeAppTheme

@Composable
fun FridgeItemDetailsWrapper(
    viewModel: FridgeItemDetailsViewModel = hiltViewModel(),
    id: Int,
    onBack: () -> Unit,
    onEditClick: () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()


    FridgeItemDetailsScreen(
        screenState = screenState,
        onBack = onBack,
        onEditClick = onEditClick,
        onDeleteClick =  {

        }
    )

}

@Composable
fun FridgeItemDetailsScreen(
    screenState: FridgeItemScreenStates,
    onBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
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
                        onBack()
                    },
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = R.string.fridgeItemDetails),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = -(40.dp))
                    .clickable {
                        onEditClick()
                    },
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onDeleteClick()
                    },
                painter = painterResource(R.drawable.ic_delete_forever),
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        when (screenState) {
            is Empty -> {

            }
            is Error -> {

            }
            is Loading -> {

            }
            is Success -> {
                val item = screenState.item
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 32.dp),
                        text = item.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    item.note?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        SectionItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            sectionTitle = stringResource(R.string.note),
                            icon = painterResource(R.drawable.ic_note),
                            text = it
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    SectionItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sectionTitle = stringResource(R.string.expiredDate),
                        icon = painterResource(R.drawable.ic_expire_date),
                        text = item.expiredDate.getDayMonthYear()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    SectionItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sectionTitle = stringResource(R.string.timeOfStorage),
                        icon = painterResource(R.drawable.ic_time_stored),
                        text = item.timeStored.getDayMonthYearHourMinutes()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    SectionItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sectionTitle = stringResource(R.string.packageState),
                        icon = painterResource(
                            if (item.isOpen) {
                                R.drawable.ic_open
                            } else {
                                R.drawable.ic_lock
                            }
                        ),
                        text = stringResource(
                            if (item.isOpen) {
                                R.string.opened
                            } else {
                                R.string.closed
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    SectionItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sectionTitle = stringResource(R.string.category),
                        icon = painterResource(R.drawable.ic_category),
                        text = item.category
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FridgeItemDetailsScreenPreview() {
    FridgeAppTheme {
        FridgeItemDetailsScreen(
//            item =  FridgeItem(
//                id = 1,
//                name = "Eggs",
//                category = "Protein",
//                isOpen = false,
//                note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
//                expiredDate = LocalDate.now().plusDays(5),
//                timeStored = Instant.now(),
//            ),
            screenState = Loading,
            onBack = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}