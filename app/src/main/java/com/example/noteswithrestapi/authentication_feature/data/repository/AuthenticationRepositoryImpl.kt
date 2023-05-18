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
import com.example.noteswithrestapi.core.domain.model.AppResponse
import com.example.noteswithrestapi.core.data.network.getAppErrorByHttpErrorCode
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val accountsApiService: AccountsApiService,
    private val tokenManager: TokenManager,
) : AuthenticationRepository {

    override suspend fun login(loginCredentials: LoginCredentials): AppResponse<String> {
        try {
            val result = accountsApiService.login(loginCredentials)

            if (result.isSuccessful) {
                val authToken = result.body()?.loginResultDataDto?.token
                    ?: return AppResponse.failed(AppError.GeneralError(R.string.error_login_failed))
                tokenManager.saveToken(authToken)

                return AppResponse.success(authToken)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_login_failed))
        }
    }

    override suspend fun logout(): AppResponse<Unit> {
        try {
            val token = tokenManager.authToken ?: return AppResponse.Success(Unit)
            val result = accountsApiService.logout("Token $token")

            if (result.isSuccessful) {
                tokenManager.removeToken()
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_logout_failed))
        }
    }

    override suspend fun isLoggedIn(): AppResponse<Boolean> {
        try {
            val token = tokenManager.authToken ?: return AppResponse.Success(false)
            val result = accountsApiService.isAuthenticated(token)

            if (result.isSuccessful) {
                return AppResponse.success(true)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_login_failed))
        }
    }

    override suspend fun register(registerCredentials: RegisterCredentials): AppResponse<Unit> {
        try {
            val result = accountsApiService.register(registerCredentials)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_register_failed))
        }
    }

    override suspend fun verifyEmail(verifyEmailCredentials: VerifyEmailCredentials): AppResponse<Unit> {
        try {
            val result = accountsApiService.verifyEmail(verifyEmailCredentials)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_verify_email_failed))
        }
    }

    override suspend fun resendEmailVerification(resendEmailVerificationCredentials: ResendEmailVerificationCredentials): AppResponse<Unit> {
        try {
            val result = accountsApiService.resendEmailVerification(resendEmailVerificationCredentials)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_resend_code_failed))
        }
    }

    override suspend fun sendPasswordResetCode(sendPasswordResetCodeCredentials: SendPasswordResetCodeCredentials): AppResponse<Unit> {
        try {
            val result = accountsApiService.sendPasswordResetCode(sendPasswordResetCodeCredentials)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_send_password_reset_failed))
        }
    }

    override suspend fun confirmPasswordReset(passwordResetConfirmCredentials: PasswordResetConfirmCredentials): AppResponse<Unit> {
        try {
            val result = accountsApiService.passwordResetConfirm(passwordResetConfirmCredentials)

            if (result.isSuccessful) {
                return AppResponse.success(Unit)
            }

            val error = result.getAppErrorByHttpErrorCode()
            return AppResponse.failed(error)

        } catch (e: IOException) {
            return AppResponse.failed(AppError.PoorNetworkConnectionError)
        } catch (e: HttpException) {
            return AppResponse.failed(AppError.GeneralError(R.string.error_password_reset_failed))
        }
    }


}