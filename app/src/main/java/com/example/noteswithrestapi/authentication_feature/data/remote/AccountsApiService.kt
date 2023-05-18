package com.example.noteswithrestapi.authentication_feature.data.remote

import com.example.noteswithrestapi.authentication_feature.data.remote.dto.EmailVerificationResendResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.EmailVerificationResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.IsAuthenticatedResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.LoginResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.RegisterResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.ResetPasswordConfirmResultDto
import com.example.noteswithrestapi.authentication_feature.data.remote.dto.ResetPasswordResultDto
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.RegisterCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApiService {

    @POST("accounts/login/")
    suspend fun login(
        @Body loginCredentials: LoginCredentials,
    ): Response<LoginResultDto>


    @POST("accounts/check-token/")
    suspend fun isAuthenticated(
        @Body token: String,
    ): Response<IsAuthenticatedResultDto>


    @POST("accounts/register/")
    suspend fun register(
        @Body registerCredentials: RegisterCredentials,
    ): Response<RegisterResultDto>


    @POST("accounts/verify-email/")
    suspend fun verifyEmail(
        @Body verifyEmailCredentials: VerifyEmailCredentials,
    ): Response<EmailVerificationResultDto>


    @POST("accounts/verify-email/resend/")
    suspend fun resendEmailVerification(
        @Body resendEmailVerificationCredentials: ResendEmailVerificationCredentials,
    ): Response<EmailVerificationResendResultDto>


    @POST("accounts/reset-password/")
    suspend fun sendPasswordResetCode(
        @Body sendPasswordResetCodeCredentials: SendPasswordResetCodeCredentials,
    ): Response<ResetPasswordResultDto>


    @POST("accounts/reset-password/confirm/")
    suspend fun passwordResetConfirm(
        @Body passwordResetConfirmCredentials: PasswordResetConfirmCredentials,
    ): Response<ResetPasswordConfirmResultDto>


}