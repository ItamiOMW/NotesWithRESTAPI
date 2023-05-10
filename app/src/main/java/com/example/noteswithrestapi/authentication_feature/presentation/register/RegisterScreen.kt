package com.example.noteswithrestapi.authentication_feature.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun RegisterScreen(
    onRegisterSuccessful: (email: String) -> Unit, //Navigate to verify email screen after registration
    onNavigateToLogin: () -> Unit,
    state: RegisterState,
    uiEvent: Flow<RegisterUiEvent>,
    onEvent: (RegisterEvent) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is RegisterUiEvent.OnRegisterSuccessful -> onRegisterSuccessful(event.email)
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.amico_signup),
                contentDescription = stringResource(
                    R.string.desc_sign_up_illustration
                ),
                modifier = Modifier.size(230.dp, 200.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_register),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 1.dp)
                )
                Text(
                    text = stringResource(R.string.hint_fill_inputs),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            InputFieldComponent(
                textValue = { state.emailInput },
                onValueChanged = { newValue ->
                    onEvent(
                        RegisterEvent.OnEmailInputValueChange(
                            newValue
                        )
                    )
                },
                error = { state.emailErrorMessage },
                label = stringResource(R.string.label_email),
                leadingIcon = Icons.Rounded.Email,
                enabled = { !state.isLoading },
                modifier = inputFieldDefaultModifier,
            )
            Spacer(modifier = Modifier.height(28.dp))
            InputFieldComponent(
                textValue = { state.passwordInput },
                onValueChanged = { newValue ->
                    onEvent(
                        RegisterEvent.OnPasswordInputValueChange(
                            newValue
                        )
                    )
                },
                error = { state.passwordErrorMessage },
                label = stringResource(R.string.label_password),
                leadingIcon = Icons.Rounded.Lock,
                trailingIcon = Icons.Rounded.RemoveRedEye,
                onTrailingIconClicked = { onEvent(RegisterEvent.OnChangePasswordVisualTransformation) },
                visualTransformation = if (state.isPasswordShown) VisualTransformation.None
                else PasswordVisualTransformation(),
                enabled = { !state.isLoading },
                modifier = inputFieldDefaultModifier
            )
            Spacer(modifier = Modifier.height(28.dp))
            InputFieldComponent(
                textValue = { state.confirmPasswordInput },
                onValueChanged = { newValue ->
                    onEvent(
                        RegisterEvent.OnConfirmPasswordInputValueChange(
                            newValue
                        )
                    )
                },
                error = { state.confirmPasswordErrorMessage },
                label = stringResource(R.string.label_confirm_password),
                leadingIcon = Icons.Rounded.Lock,
                trailingIcon = Icons.Rounded.RemoveRedEye,
                onTrailingIconClicked = { onEvent(RegisterEvent.OnChangeConfirmPasswordVisualTransformation) },
                visualTransformation = if (state.isConfirmPasswordShown) VisualTransformation.None
                else PasswordVisualTransformation(),
                enabled = { !state.isLoading },
                modifier = inputFieldDefaultModifier
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
                text = stringResource(id = R.string.text_sign_up),
                enabled = { !state.isLoading },
                onButtonClick = { onEvent(RegisterEvent.OnSignUpClick) },
                leadingIcon = Icons.Default.HowToReg
            )
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.text_have_account),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(
                        onClick = {
                            onNavigateToLogin()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.text_sign_in),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RegisterScreenPreview() {
    NotesWithRESTAPITheme {
        RegisterScreen(
            onRegisterSuccessful = {},
            onNavigateToLogin = { },
            state = RegisterState(),
            uiEvent = flow {},
            onEvent = {}
        )
    }
}