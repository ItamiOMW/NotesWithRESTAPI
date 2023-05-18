package com.example.noteswithrestapi.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme


@Composable
fun LoadingFailedComponent(
    errorMessage: String,
    onRetryClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        if (onRetryClick != null) {
            Text(
                text = stringResource(R.string.text_try_again),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                onClick = {
                    onRetryClick()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Replay,
                    contentDescription = stringResource(R.string.desc_retry_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

}


@Preview
@Composable
fun LoadingFailedComponentPreview() {
    NotesWithRESTAPITheme {
        LoadingFailedComponent(
            errorMessage = "Failed to load notes",
            onRetryClick = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}