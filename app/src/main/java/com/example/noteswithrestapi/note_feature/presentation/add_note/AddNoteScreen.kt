package com.example.noteswithrestapi.note_feature.presentation.add_note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.components.ButtonComponent
import com.example.noteswithrestapi.core.presentation.components.OutlinedButtonComponent
import com.example.noteswithrestapi.core.presentation.components.ProgressIndicatorComponent
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    onSavedSuccessfully: () -> Unit,
    onCancel: () -> Unit,
    state: AddNoteState,
    uiEvent: Flow<AddNoteUiEvent>,
    onEvent: (AddNoteEvent) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is AddNoteUiEvent.OnNoteSaved -> onSavedSuccessfully()

                is AddNoteUiEvent.OnShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_add_note),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onCancel
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.desc_go_back),
                        )
                    }
                }
            )
        },
        bottomBar = {
            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 30.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonComponent(
                    text = stringResource(R.string.title_save),
                    enabled = { !state.isLoading },
                    onButtonClick = { onEvent(AddNoteEvent.OnSaveClick) },
                    contentPadding = PaddingValues(horizontal = 26.dp, vertical = 10.dp)
                )
                OutlinedButtonComponent(
                    text = stringResource(R.string.title_cancel),
                    enabled = { !state.isLoading },
                    onButtonClick = onCancel,
                    contentPadding = PaddingValues(horizontal = 26.dp, vertical = 10.dp)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                TitleInputTextField(
                    inputValue = { state.titleInputValue },
                    onValueChange = { onEvent(AddNoteEvent.OnTitleInputValueChange(it)) },
                    isLoading = { state.isLoading }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))
                ContentInputTextField(
                    inputValue = { state.contentInputValue },
                    isLoading = { state.isLoading },
                    onValueChange = { onEvent(AddNoteEvent.OnContentInputValueChange(it)) }
                )
            }
            ProgressIndicatorComponent(isLoading = { state.isLoading })
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TitleInputTextField(
    inputValue: () -> String,
    isLoading: () -> Boolean,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        value = inputValue(),
        onValueChange = onValueChange,
        enabled = !isLoading(),
        textStyle = MaterialTheme.typography.titleMedium,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MaterialTheme.colorScheme.onBackground
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.text_note_title),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentInputTextField(
    inputValue: () -> String,
    isLoading: () -> Boolean,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(unbounded = false)
            .padding(start = 28.dp, end = 28.dp),
        value = inputValue(),
        onValueChange = onValueChange,
        enabled = !isLoading(),
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = false,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MaterialTheme.colorScheme.onBackground
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.text_type_smth),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    )
}


@Preview
@Composable
fun AddNoteScreenPreview() {
    NotesWithRESTAPITheme {
        AddNoteScreen(
            onSavedSuccessfully = { },
            onCancel = {},
            state = AddNoteState(),
            uiEvent = flow {},
            onEvent = {}
        )
    }
}