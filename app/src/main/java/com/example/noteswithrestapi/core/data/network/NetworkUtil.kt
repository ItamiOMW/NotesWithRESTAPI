package com.example.noteswithrestapi.core.data.network

import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import com.example.noteswithrestapi.authentication_feature.domain.error.AuthenticationAppError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response

fun createRequestBody(vararg params: Pair<String, Any>) =
    JSONObject(mapOf(*params)).toString()
        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


fun <T> Response<T>.getAppErrorByHttpErrorCode(): AppError {
    val code = this.getHttpErrorCode() ?: return AppError.GeneralError(R.string.error_unknown)
    if (code.toInt() in 500..599) {
        return AppError.ServerError
    }
    return parseHttpErrorCodeToAppError(code)
}

fun parseHttpErrorCodeToAppError(code: String): AppError {
    return when (code) {

        HttpCustomErrorCode.InvalidVerificationCode.code -> AuthenticationAppError.InvalidVerificationCodeError
        HttpCustomErrorCode.ShortPassword.code -> AuthenticationAppError.ShortPasswordError
        HttpCustomErrorCode.InvalidVerificationCode.code -> AuthenticationAppError.InvalidVerificationCodeError
        HttpCustomErrorCode.InvalidPasswordResetCode.code -> AuthenticationAppError.InvalidVerificationCodeError
        HttpCustomErrorCode.InvalidEmail.code -> AuthenticationAppError.InvalidEmailError
        HttpCustomErrorCode.UsedAlreadyExist.code -> AuthenticationAppError.UserAlreadyExist
        HttpCustomErrorCode.UsedDoesNotExist.code -> AuthenticationAppError.UserDoesNotExist
        HttpCustomErrorCode.InvalidEmailOrPassword.code -> AuthenticationAppError.InvalidEmailOrPasswordError
        HttpCustomErrorCode.EmailNotVerified.code -> AuthenticationAppError.EmailNotVerifiedError

        HttpCustomErrorCode.InvalidCredentials.code -> AppError.InvalidCredentialsError
        HttpCustomErrorCode.NotFound.code -> AppError.NotFoundError
        HttpCustomErrorCode.Unauthorized.code -> AppError.UnauthorizedError
        HttpCustomErrorCode.Forbidden.code -> AppError.ForbiddenError
        else -> AppError.GeneralError(R.string.error_unknown)
    }
}


fun <T> Response<T>.getHttpErrorCode(): String? {
    try {
        if (this.code() in 500..599) {
            return this.code().toString()
        }
        if (this.code() in 400..499) {
            return this.errorBody()?.string()?.let { jsonString ->
                JSONObject(jsonString).getString("code")
            } ?: this.code().toString()
        }
        return null
    }  catch (e: Exception) {
        return this.code().toString()
    }
}