package com.example.noteswithrestapi.authentication_feature.domain.repository

import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.RegisterCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import com.example.noteswithrestapi.core.domain.model.Response

interface AuthenticationRepository {

    suspend fun login(loginCredentials: LoginCredentials): Response<String> //Return token

    suspend fun logout(): Response<Unit>

    suspend fun isLoggedIn(): Response<Boolean>

    suspend fun register(registerCredentials: RegisterCredentials): Response<Unit>

    suspend fun verifyEmail(verifyEmailCredentials: VerifyEmailCredentials): Response<Unit>

    suspend fun resendEmailVerification(
        resendEmailVerificationCredentials: ResendEmailVerificationCredentials,
    ): Response<Unit>

    suspend fun sendPasswordResetCode(sendPasswordResetCodeCredentials: SendPasswordResetCodeCredentials): Response<Unit>

    suspend fun confirmPasswordReset(passwordResetConfirmCredentials: PasswordResetConfirmCredentials): Response<Unit>

}