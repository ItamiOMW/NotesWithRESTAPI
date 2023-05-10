package com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.presentation.components.OTPTextField
import com.example.noteswithrestapi.core.presentation.components.ButtonComponent
import com.example.noteswithrestapi.core.presentation.components.InputFieldComponent
import com.example.noteswithrestapi.core.presentation.components.ProgressIndicatorComponent
import com.example.noteswithrestapi.core.presentation.components.inputFieldDefaultModifier
import com.example.noteswithrestapi.core.presentation.theme.ui.theme.NotesWithRESTAPITheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordResetScreen(
    onConfirmPasswordResetSuccessful: () -> Unit,
    state: ConfirmPasswordResetState,
    uiEvent: Flow<ConfirmPasswordResetUiEvent>,
    onEvent: (ConfirmPasswordResetEvent) -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                ConfirmPasswordResetUiEvent.OnConfirmPasswordResetSuccess -> {
                    onConfirmPasswordResetSuccessful()
                }

                is ConfirmPasswordResetUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message, duration = SnackbarDuration.Short)
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
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
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.amico_reset),
                contentDescription = stringResource(
                    R.string.desc_password_reset_illustration
                ),
                modifier = Modifier.size(230.dp, 200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_reset_password),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 1.dp)
                )
                Text(
                    text = stringResource(R.string.hint_enter_code),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            OTPTextField(
                modifier = Modifier
                    .wrapContentHeight(),
                textValue = { state.codeInput },
                onTextValueChange = { newValue ->
                    onEvent(
                        ConfirmPasswordResetEvent.OnCodeInputValueChange(
                            newValue
                        )
                    )
                },
                charSize = 26.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            InputFieldComponent(
                textValue = { state.passwordInput },
                onValueChanged = { newValue ->
                    onEvent(
                        ConfirmPasswordResetEvent.OnPasswordInputValueChange(newValue)
                    )
                },
                error = { state.passwordError },
                label = stringResource(R.string.label_new_password),
                leadingIcon = Icons.Rounded.Lock,
                enabled = { !state.isLoading },
                visualTransformation = if (state.isPasswordShown) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = Icons.Rounded.RemoveRedEye,
                onTrailingIconClicked = {
                    onEvent(ConfirmPasswordResetEvent.OnChangePasswordVisualTransformation)
                },
                modifier = inputFieldDefaultModifier,
            )
            Spacer(modifier = Modifier.height(28.dp))
            InputFieldComponent(
                textValue = { state.confirmPasswordInput },
                onValueChanged = { newValue ->
                    onEvent(
                        ConfirmPasswordResetEvent.OnConfirmPasswordInputValueChange(newValue)
                    )
                },
                error = { state.confirmPasswordError },
                label = stringResource(R.string.label_confirm_password),
                leadingIcon = Icons.Rounded.Lock,
                enabled = { !state.isLoading },
                visualTransformation = if (state.isConfirmPasswordShown) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = Icons.Rounded.RemoveRedEye,
                onTrailingIconClicked = {
                    onEvent(ConfirmPasswordResetEvent.OnChangeConfirmPasswordVisualTransformation)
                },
                modifier = inputFieldDefaultModifier,
            )
            Spacer(modifier = Modifier.height(14.dp))
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            ButtonComponent(
                text = stringResource(id = R.string.text_confirm),
                enabled = { !state.isLoading },
                onButtonClick = { onEvent(ConfirmPasswordResetEvent.OnConfirmClick) },
                leadingIcon = Icons.Rounded.Done
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(
                onClick = { onEvent(ConfirmPasswordResetEvent.OnResendCodeClick) }
            ) {
                Text(
                    text = stringResource(R.string.text_resend_code),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}

@Preview
@Composable
fun ConfirmPasswordResetScreenPreview() {
    NotesWithRESTAPITheme {
        ConfirmPasswordResetScreen(
            onConfirmPasswordResetSuccessful = { },
            state = ConfirmPasswordResetState(),
            uiEvent = flow {},
            onEvent = {}
        )
    }
}