package com.example.noteswithrestapi.authentication_feature.data.repository

import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.authentication_feature.data.remote.AccountsApiService
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.LoginCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.PasswordResetConfirmCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.RegisterCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.ResendEmailVerificationCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.SendPasswordResetCodeCredentials
import com.example.noteswithrestapi.authentication_feature.domain.model.credentials.VerifyEmailCredentials
import com.example.noteswithrestapi.authentication_feature.domain.repository.AuthenticationRepository
import com.example.noteswithrestapi.core.data.token.TokenManager
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.core.domain.model.Response
import com.example.noteswithrestapi.core.network.getAppErrorByHttpErrorCode
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val accountsApiService: AccountsApiService,
    private val tokenManager: TokenManager,
) : AuthenticationRepository {

    override suspend fun login(loginCredentials: LoginCredentials): Response<String> {
        try {
            val result = accountsApiService.login(loginCredentials)

            if (result.isSuccessful) {
                val authToken = result.body()?.loginResultDataDto?.token
                    ?: return Response.failed(AppError.GeneralError(R.string.error_login_failed))
                tokenManager.saveToken(authToken)

                return Response.success(authToken)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_login_failed))
        }
    }

    override suspend fun logout(): Response<Unit> {
        try {
            val token = tokenManager.authToken ?: return Response.Success(Unit)
            val result = accountsApiService.logout("Token $token")

            if (result.isSuccessful) {
                tokenManager.removeToken()
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_logout_failed))
        }
    }

    override suspend fun isLoggedIn(): Response<Boolean> {
        try {
            val token = tokenManager.authToken ?: return Response.Success(false)
            val result = accountsApiService.isAuthenticated(token)

            if (result.isSuccessful) {
                return Response.success(true)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_login_failed))
        }
    }

    override suspend fun register(registerCredentials: RegisterCredentials): Response<Unit> {
        try {
            val result = accountsApiService.register(registerCredentials)

            if (result.isSuccessful) {
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_register_failed))
        }
    }

    override suspend fun verifyEmail(verifyEmailCredentials: VerifyEmailCredentials): Response<Unit> {
        try {
            val result = accountsApiService.verifyEmail(verifyEmailCredentials)

            if (result.isSuccessful) {
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_verify_email_failed))
        }
    }

    override suspend fun resendEmailVerification(resendEmailVerificationCredentials: ResendEmailVerificationCredentials): Response<Unit> {
        try {
            val result = accountsApiService.resendEmailVerification(resendEmailVerificationCredentials)

            if (result.isSuccessful) {
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_resend_code_failed))
        }
    }

    override suspend fun sendPasswordResetCode(sendPasswordResetCodeCredentials: SendPasswordResetCodeCredentials): Response<Unit> {
        try {
            val result = accountsApiService.sendPasswordResetCode(sendPasswordResetCodeCredentials)

            if (result.isSuccessful) {
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_send_password_reset_failed))
        }
    }

    override suspend fun confirmPasswordReset(passwordResetConfirmCredentials: PasswordResetConfirmCredentials): Response<Unit> {
        try {
            val result = accountsApiService.passwordResetConfirm(passwordResetConfirmCredentials)

            if (result.isSuccessful) {
                return Response.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return Response.failed(error)

        } catch (e: IOException) {
            return Response.failed(AppError.PoorNetworkConnection)
        } catch (e: HttpException) {
            return Response.failed(AppError.GeneralError(R.string.error_password_reset_failed))
        }
    }


}