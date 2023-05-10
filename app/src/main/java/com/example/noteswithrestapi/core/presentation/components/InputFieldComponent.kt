package com.example.noteswithrestapi.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme


val inputFieldDefaultModifier = Modifier
    .padding(start = 25.dp, end = 25.dp)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFieldComponent(
    modifier: Modifier = Modifier,
    textValue: () -> String,
    onValueChanged: (String) -> Unit,
    error: () -> String?,
    label: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
    onTrailingIconClicked: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color = MaterialTheme.colorScheme.primary,
    labelColor: Color = MaterialTheme.colorScheme.onBackground,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedBackgroundColor: Color = Color.Transparent,
    errorColor: Color = MaterialTheme.colorScheme.error,
    enabled: () -> Boolean = { true },
) {

    val textFieldInteractionSource = remember { MutableInteractionSource() }

    val isFocused = textFieldInteractionSource.collectIsFocusedAsState()


    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = error() != null) {
            error()?.let { error ->
                Text(
                    text = error,
                    color = errorColor,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .height(60.dp),
            value = textValue(),
            onValueChange = onValueChanged,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = if (isFocused.value) selectedBackgroundColor else unselectedBackgroundColor,
                disabledIndicatorColor = Color.Transparent,
            ),
            interactionSource = textFieldInteractionSource,
            maxLines = 1,
            enabled = enabled(),
            label = {
                Box(modifier = Modifier.fillMaxHeight(0.55f)) {
                    Text(
                        text = label,
                        color = labelColor,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = if (isFocused.value) 12.sp else 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = stringResource(R.string.desc_leading_icon),
                    modifier = Modifier.size(32.dp),
                    tint = textColor
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onTrailingIconClicked?.invoke()
                    }
                ) {
                    if (trailingIcon != null) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = stringResource(R.string.desc_leading_icon),
                            modifier = Modifier.size(27.dp),
                            tint = textColor
                        )
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun InputFieldComponentPreview() {
    NotesWithRESTAPITheme(darkTheme = true) {
        InputFieldComponent(
            textValue = { "" },
            onValueChanged = {},
            label = "EMAIL",
            error = { "Maga shooher" },
            leadingIcon = Icons.Rounded.Email
        )
    }
}