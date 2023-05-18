package com.example.noteswithrestapi.core.data.network

enum class HttpCustomErrorCode(val code: String) {

    //Authentication errors
    InvalidToken("4001"),
    InvalidEmailOrPassword("4002"),
    InvalidEmail("4003"),
    EmailAlreadyVerified("4004"),
    EmailNotVerified("4005"),
    UsedDoesNotExist("4006"),
    UsedAlreadyExist("4007"),
    InvalidVerificationCode("4008"),
    InvalidPasswordResetCode("4009"),
    InvalidCredentials("4010"),
    ShortPassword("4011"),

    NotFound("404"),
    Unauthorized("401"),
    Forbidden("403"),

}