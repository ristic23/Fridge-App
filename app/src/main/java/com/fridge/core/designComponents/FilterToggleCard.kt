package com.fridge.core.designComponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterToggleCard(
    modifier: Modifier = Modifier,
    text: String,
    isOn: Boolean,
    onCardClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isOn)
            MaterialTheme.colorScheme.primaryContainer
        else
            Color.Transparent,
        animationSpec = tween(500)
    )

    val borderColor by animateColorAsState(
        targetValue = if (isOn)
            Color.Transparent
        else
            MaterialTheme.colorScheme.onPrimaryContainer,
        animationSpec = tween(500)
    )

    Card(
        modifier = modifier,
        onClick = onCardClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}