package com.example.noteswithrestapi.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import com.example.noteswithrestapi.core.ui.theme.NotesWithRESTAPITheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InputFieldComponent(
    modifier: Modifier = Modifier,
    textValue: String,
    onValueChanged: (String) -> Unit,
    label: String,
    error: String?,
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
) {

    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = error != null) {
            if (error != null) {
                Text(
                    text = error,
                    color = errorColor,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .height(60.dp),
            value = textValue,
            onValueChange = onValueChanged,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
            ),
            maxLines = 1,
            label = {
                Text(
                    text = label,
                    color = labelColor,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = stringResource(R.string.desc_leading_icon),
                    modifier = Modifier.size(33.dp),
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
                            modifier = Modifier.size(33.dp),
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
            textValue = "",
            onValueChanged = {},
            label = "EMAIL",
            error = null,
            leadingIcon = Icons.Rounded.Email
        )
    }
}