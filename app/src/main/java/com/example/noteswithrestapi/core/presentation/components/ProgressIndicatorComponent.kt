package com.example.noteswithrestapi.core.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun ProgressIndicatorComponent(
    isLoading: () -> Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
) {

    if (isLoading()) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary
        )
    }

}