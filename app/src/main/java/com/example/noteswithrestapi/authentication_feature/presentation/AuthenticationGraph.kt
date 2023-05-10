package com.example.noteswithrestapi.authentication_feature.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset.ConfirmPasswordResetScreen
import com.example.noteswithrestapi.authentication_feature.presentation.confirm_password_reset.ConfirmPasswordResetViewModel
import com.example.noteswithrestapi.authentication_feature.presentation.login.LoginScreen
import com.example.noteswithrestapi.authentication_feature.presentation.login.LoginViewModel
import com.example.noteswithrestapi.authentication_feature.presentation.register.RegisterScreen
import com.example.noteswithrestapi.authentication_feature.presentation.register.RegisterViewModel
import com.example.noteswithrestapi.authentication_feature.presentation.reset_password.ResetPasswordScreen
import com.example.noteswithrestapi.authentication_feature.presentation.reset_password.ResetPasswordViewModel
import com.example.noteswithrestapi.authentication_feature.presentation.verify_email.VerifyEmailScreen
import com.example.noteswithrestapi.authentication_feature.presentation.verify_email.VerifyEmailViewModel
import com.example.noteswithrestapi.core.presentation.navigation.Graph
import com.example.noteswithrestapi.core.presentation.navigation.Screen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authGraph(
    navController: NavController,
) {
    navigation(
        route = Graph.AUTH_GRAPH,
        startDestination = Screen.Login.fullRoute
    ) {
        composable(Screen.Login.fullRoute) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                onNavigateToResetPassword = {
                    navController.navigate(Screen.ResetPassword.fullRoute) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.fullRoute) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                inclusive = true
                            }
                        }
                    }
                },
                onNavigateToVerifyEmail = { email ->
                    navController.navigate(Screen.VerifyEmail.getRouteWithArgs(email)) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onLoginSuccessful = {
                    navController.navigate(Graph.NOTE_GRAPH) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    navController.graph.setStartDestination(Graph.NOTE_GRAPH)
                },
                state = loginViewModel.loginState,
                uiEvent = loginViewModel.uiEvent,
                onEvent = loginViewModel::onEvent
            )
        }
        composable(Screen.Register.fullRoute) {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                onRegisterSuccessful = { email ->
                    navController.navigate(
                        Screen.VerifyEmail.getRouteWithArgs(email)
                    ) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.fullRoute) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                inclusive = true
                            }
                        }
                    }
                },
                state = viewModel.registerState,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            Screen.VerifyEmail.fullRoute,
            arguments = listOf(
                navArgument(Screen.EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: VerifyEmailViewModel = hiltViewModel()
            VerifyEmailScreen(
                onEmailVerifiedSuccessfully = {
                    navController.navigate(Screen.Login.fullRoute) {
                        popUpTo(0)
                    }
                },
                state = viewModel.verifyEmailState,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.ResetPassword.fullRoute) {
            val viewModel: ResetPasswordViewModel = hiltViewModel()
            ResetPasswordScreen(
                onResetCodeSentSuccessfully = { email ->
                    navController.navigate(
                        Screen.ConfirmPasswordReset.getRouteWithArgs(email)
                    ) {
                        navController.currentDestination?.id?.let { id ->
                            this.popUpTo(id) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                state = viewModel.resetPasswordState,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            Screen.ConfirmPasswordReset.fullRoute,
            arguments = listOf(
                navArgument(Screen.EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: ConfirmPasswordResetViewModel = hiltViewModel()
            ConfirmPasswordResetScreen(
                onConfirmPasswordResetSuccessful = {
                    navController.navigate(Screen.Login.fullRoute) {
                        popUpTo(0)
                    }
                },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
    }
}