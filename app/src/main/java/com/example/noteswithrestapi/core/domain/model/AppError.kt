package com.example.noteswithrestapi.core.domain.model

sealed class AppError(
    message: String? = null,
    cause: Exception? = null
): Error(message, cause) {

    //Common Errors
    data class GeneralError(val messageResId: Int): AppError()

    object PoorNetworkConnection: AppError()

    object ServerError: AppError()

    object InvalidCredentials: AppError()


    //Authentication errors
    object EmptyEmailError: AppError()

    object EmptyPasswordError: AppError()

    object EmptyRepeatPasswordError: AppError()

    object InvalidEmailOrPasswordError: AppError()

    object InvalidVerificationCodeError: AppError()

    object PasswordsDoNotMatchError: AppError()

    object ShortPasswordError: AppError()

    object UserAlreadyExist: AppError()

    object UserDoesNotExist: AppError()

    object InvalidEmailError: AppError()

    object EmailNotVerifiedError: AppError()

}