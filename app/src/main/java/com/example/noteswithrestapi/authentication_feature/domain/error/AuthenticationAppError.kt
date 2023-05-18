package com.example.noteswithrestapi.authentication_feature.domain.error

import com.example.noteswithrestapi.core.domain.model.AppError

open class AuthenticationAppError: AppError() {

    object EmptyEmailError: AuthenticationAppError()

    object EmptyPasswordError: AuthenticationAppError()

    object EmptyRepeatPasswordError: AuthenticationAppError()

    object InvalidEmailOrPasswordError: AuthenticationAppError()

    object InvalidVerificationCodeError: AuthenticationAppError()

    object PasswordsDoNotMatchError: AuthenticationAppError()

    object ShortPasswordError: AuthenticationAppError()

    object UserAlreadyExist: AuthenticationAppError()

    object UserDoesNotExist: AuthenticationAppError()

    object InvalidEmailError: AuthenticationAppError()

    object EmailNotVerifiedError: AuthenticationAppError()

}
