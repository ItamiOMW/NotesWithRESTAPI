package com.example.noteswithrestapi.authentication_feature.domain.repository

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.RegisterCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import com.example.noteswithrestapi.core.domain.model.AppResponse

interface AuthenticationRepository {

    suspend fun login(loginCredentials: LoginCredentials): AppResponse<String> //Return token

    suspend fun logout(): AppResponse<Unit>

    suspend fun isLoggedIn(): AppResponse<Boolean>

    suspend fun register(registerCredentials: RegisterCredentials): AppResponse<Unit>

    suspend fun verifyEmail(verifyEmailCredentials: VerifyEmailCredentials): AppResponse<Unit>

    suspend fun resendEmailVerification(
        resendEmailVerificationCredentials: ResendEmailVerificationCredentials,
    ): AppResponse<Unit>

    suspend fun sendPasswordResetCode(sendPasswordResetCodeCredentials: SendPasswordResetCodeCredentials): AppResponse<Unit>

    suspend fun confirmPasswordReset(passwordResetConfirmCredentials: PasswordResetConfirmCredentials): AppResponse<Unit>

}