package com.example.noteswithrestapi.authentication_feature.presentation.login

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
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Login
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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToVerifyEmail: (email: String) -> Unit,
    onNavigateToResetPassword: () -> Unit,
    onLoginSuccessful: () -> Unit,
    state: LoginState,
    uiEvent: Flow<LoginUiEvent>,
    onEvent: (LoginEvent) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.SuccessfullyLoggedIn -> onLoginSuccessful()

                is LoginUiEvent.EmailNotVerified -> onNavigateToVerifyEmail(event.email)
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
            Spacer(modifier = Modifier.height(64.dp))
            Image(
                painter = painterResource(id = R.drawable.amico_signin),
                contentDescription = stringResource(
                    R.string.desc_sign_in_illustration
                ),
                modifier = Modifier.size(230.dp, 200.dp)
            )
            Spacer(modifier = Modifier.height(45.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_login),
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
            Spacer(modifier = Modifier.height(45.dp))
            InputFieldComponent(
                textValue = { state.emailInput },
                onValueChanged = { newValue -> onEvent(LoginEvent.OnEmailInputValueChange(newValue)) },
                error = { state.emailErrorMessage },
                label = stringResource(R.string.label_email),
                leadingIcon = Icons.Rounded.Email,
                enabled = { !state.isLoading },
                modifier = inputFieldDefaultModifier,
            )
            Spacer(modifier = Modifier.height(28.dp))
            InputFieldComponent(
                textValue = { state.passwordInput },
                onValueChanged = { newValue -> onEvent(LoginEvent.OnPasswordInputValueChange(newValue)) },
                error = { state.passwordErrorMessage },
                label = stringResource(R.string.label_password),
                leadingIcon = Icons.Rounded.Lock,
                trailingIcon = Icons.Rounded.RemoveRedEye,
                onTrailingIconClicked = { onEvent(LoginEvent.OnChangePasswordVisualTransformation) },
                visualTransformation = if (state.isPasswordShown) VisualTransformation.None
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
                text = stringResource(id = R.string.text_sign_in),
                enabled = { !state.isLoading },
                onButtonClick = { onEvent(LoginEvent.OnSignInClick) },
                leadingIcon = Icons.Rounded.Login
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextButton(
                onClick = {
                    onNavigateToResetPassword()
                }
            ) {
                Text(
                    text = stringResource(R.string.text_forgot_password),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.text_do_not_have_account),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(
                        onClick = {
                            onNavigateToRegister()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.text_sign_up),
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
fun LoginScreenPreview() {
    NotesWithRESTAPITheme() {
        LoginScreen(
            onNavigateToRegister = { },
            onNavigateToVerifyEmail = { },
            onNavigateToResetPassword = {},
            onLoginSuccessful = { },
            state = LoginState(),
            uiEvent = flow {},
            onEvent = {}
        )
    }
}