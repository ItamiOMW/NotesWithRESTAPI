package com.example.noteswithrestapi.authentication_feature.presentation.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.presentation.components.ButtonComponent
import com.example.noteswithrestapi.core.presentation.components.InputFieldComponent
import com.example.noteswithrestapi.core.presentation.components.ProgressIndicatorComponent
import com.example.noteswithrestapi.core.presentation.components.inputFieldDefaultModifier
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    onResetCodeSentSuccessfully: (email: String) -> Unit,
    onNavigateBack: () -> Unit,
    state: ResetPasswordState,
    uiEvent: Flow<ResetPasswordUiEvent>,
    onEvent: (ResetPasswordEvent) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is ResetPasswordUiEvent.OnResetCodeSentSuccessfully -> {
                    onResetCodeSentSuccessfully(event.email)
                }
            }
        }
    }

    Scaffold() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressIndicatorComponent(isLoading = { state.isLoading })
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .padding(start = 20.dp, top = 25.dp)
                    .align(Alignment.Start),
                onClick = {
                    onNavigateBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.desc_go_back),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Image(
                painter = painterResource(id = R.drawable.amico_confirm),
                contentDescription = stringResource(
                    R.string.desc_sign_up_illustration
                ),
                modifier = Modifier.size(230.dp, 200.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_reset_password),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.hint_enter_email),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            InputFieldComponent(
                textValue = { state.emailInput },
                onValueChanged = { newValue ->
                    onEvent(
                        ResetPasswordEvent.OnEmailInputValueChange(
                            newValue
                        )
                    )
                },
                error = { state.emailError },
                label = stringResource(R.string.label_email),
                leadingIcon = Icons.Rounded.Email,
                enabled = { !state.isLoading },
                modifier = inputFieldDefaultModifier,
            )
            Spacer(modifier = Modifier.height(21.dp))
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(21.dp))
            ButtonComponent(
                text = stringResource(id = R.string.text_reset_password),
                enabled = { !state.isLoading },
                onButtonClick = { onEvent(ResetPasswordEvent.OnResetPasswordClick) },
                leadingIcon = Icons.Default.LockReset
            )
        }
    }
}

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    NotesWithRESTAPITheme {
        ResetPasswordScreen(
            onResetCodeSentSuccessfully = { },
            onNavigateBack = { },
            state = ResetPasswordState(),
            uiEvent = flow { },
            onEvent = {}
        )
    }
}