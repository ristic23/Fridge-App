package com.fridge.features.allItems.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fridge.R
import com.fridge.ui.theme.FridgeAppTheme

@Composable
fun ItemFridge(
    modifier: Modifier = Modifier,
    name: String,
    note: String? = null,
    category: String,
    timeStored: String,
    expiredDate: String,
    isOpened: Boolean,
    isExpired: Boolean,
    onItemClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable { onItemClick() }
            .background(
                color = if (isExpired)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .then(
                            if (isOpened) {
                                Modifier
                                    .fillMaxWidth(0.85f)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                            }
                        ),
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                if (isOpened) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.opened),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            CategoryCard(
                modifier = Modifier
                    .padding(top = 8.dp),
                category = category
            )

            note?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                IconWithText(
                    modifier = Modifier
                        .weight(1f),
                    iconPainter = painterResource(id = R.drawable.ic_time_stored),
                    text = timeStored,
                    iconTint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                IconWithText(
                    modifier = Modifier
                        .weight(1f),
                    iconPainter = painterResource(id = R.drawable.ic_expire_date),
                    text = expiredDate,
                    iconTint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    iconTint: Color = Color.Unspecified,
    iconModifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    contentPadding: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = text,
            tint = iconTint,
            modifier = iconModifier
        )

        Spacer(modifier = Modifier.padding(start = contentPadding))

        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(24.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = category,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun ItemFridgePreview() {
    FridgeAppTheme {
        Column {
            ItemFridge(
                modifier = Modifier
                    .fillMaxWidth(),
                name = "Eggs",
                note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
                category = "Protein",
                timeStored = "12.12.2025.",
                expiredDate = "28.12.2025.",
                isOpened = true,
                isExpired = false,
                onItemClick = {},
            )
            ItemFridge(
                modifier = Modifier
                    .fillMaxWidth(),
                name = "Eggs",
                note = "These eggs are sourced from free-range hens that are fed a 100% organic diet.",
                category = "Protein",
                timeStored = "12.12.2025.",
                expiredDate = "28.12.2025.",
                isOpened = false,
                isExpired = false,
                onItemClick = {},
            )
            ItemFridge(
                modifier = Modifier
                    .fillMaxWidth(),
                name = "Eggs",
                category = "Protein",
                timeStored = "12.12.2025.",
                expiredDate = "28.12.2025.",
                isOpened = true,
                isExpired = false,
                onItemClick = {},
            )
            ItemFridge(
                modifier = Modifier
                    .fillMaxWidth(),
                name = "Eggs",
                category = "Protein",
                timeStored = "12.12.2025.",
                expiredDate = "28.12.2025.",
                isOpened = false,
                isExpired = true,
                onItemClick = {},
            )

        }
    }
}