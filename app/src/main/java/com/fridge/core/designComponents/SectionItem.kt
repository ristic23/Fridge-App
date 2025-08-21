package com.fridge.core.designComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionItem(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    icon: Painter,
    isError: Boolean = false,
    iconBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    text: String,
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = if (isError)
                MaterialTheme.colorScheme.error
            else
                iconTint
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = sectionTitle,
                    tint = if (isError)
                        MaterialTheme.colorScheme.error
                    else
                        iconTint
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onBackground
            )
        }
    }
}