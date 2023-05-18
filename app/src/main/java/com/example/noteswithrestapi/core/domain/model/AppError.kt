package com.example.noteswithrestapi.core.domain.model

open class AppError(
    message: String? = null,
    cause: Exception? = null
): Error(message, cause) {

    //Common Errors
    data class GeneralError(val messageResId: Int): AppError()

    object PoorNetworkConnectionError: AppError()

    object ServerError: AppError()

    object InvalidCredentialsError: AppError()

    object NotFoundError: AppError()

    object UnauthorizedError: AppError()

    object ForbiddenError: AppError()


}