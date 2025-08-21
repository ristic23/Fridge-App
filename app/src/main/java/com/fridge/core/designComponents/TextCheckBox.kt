package com.fridge.core.designComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Composable
fun TextCheckBox(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    checkModifier: Modifier = Modifier,
    text: String = "CheckBox",
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    checkboxColors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = color,
        uncheckedColor = color
    ),
    checked: Boolean = false,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    val interactionSource =
        remember { MutableInteractionSource() }

    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = textModifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { onCheckedChange(!checked) },
            text = text,
            color = textColor,
            style = textStyle,
        )

        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Checkbox(
                modifier = checkModifier,
                colors = checkboxColors,
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    }
}