package com.example.noteswithrestapi.core.network

import com.example.noteswithrestapi.R
import com.example.noteswithrestapi.core.domain.model.AppError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response

fun createRequestBody(vararg params: Pair<String, Any>) =
    JSONObject(mapOf(*params)).toString()
        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


fun <T> Response<T>.getAppErrorByHttpErrorCode(): AppError {
    val code = this.getHttpErrorCode() ?: return AppError.GeneralError(R.string.error_unknown)
    return parseHttpErrorCodeToAppError(code)
}

fun parseHttpErrorCodeToAppError(code: String): AppError {
    return when (code) {
        HttpCustomErrorCode.InvalidVerificationCode.code -> AppError.InvalidVerificationCodeError
        HttpCustomErrorCode.ShortPassword.code -> AppError.ShortPasswordError
        HttpCustomErrorCode.InvalidVerificationCode.code -> AppError.InvalidVerificationCodeError
        HttpCustomErrorCode.InvalidPasswordResetCode.code -> AppError.InvalidVerificationCodeError
        HttpCustomErrorCode.InvalidCredentials.code -> AppError.InvalidCredentials
        HttpCustomErrorCode.InvalidEmail.code -> AppError.InvalidEmailError
        HttpCustomErrorCode.UsedAlreadyExist.code -> AppError.UserAlreadyExist
        HttpCustomErrorCode.UsedDoesNotExist.code -> AppError.UserDoesNotExist
        HttpCustomErrorCode.InvalidEmailOrPassword.code -> AppError.InvalidEmailOrPasswordError
        HttpCustomErrorCode.EmailNotVerified.code -> AppError.EmailNotVerifiedError
        else -> AppError.GeneralError(R.string.error_unknown)
    }
}


fun <T> Response<T>.getHttpErrorCode(): String? {
    return try {
        this.errorBody()?.string()?.let { jsonString ->
            JSONObject(jsonString).getString("code")
        } ?: this.code().toString()
    }  catch (e: Exception) {
        return null
    }
}