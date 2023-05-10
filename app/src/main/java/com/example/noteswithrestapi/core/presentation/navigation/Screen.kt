package com.example.noteswithrestapi.core.presentation.navigation

sealed class Screen(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }


    //Authentication feature
    object Login: Screen(LOGIN_SCREEN_ROUTE)

    object Register: Screen(REGISTER_SCREEN_ROUTE)

    object ResetPassword: Screen(RESET_PASSWORD_SCREEN_ROUTE)

    object ConfirmPasswordReset: Screen(CONFIRM_PASSWORD_RESET_SCREEN_ROUTE, EMAIL_ARG) {
        fun getRouteWithArgs(email: String) = route.appendParams(
            EMAIL_ARG to email
        )
    }

    object VerifyEmail: Screen(VERIFY_EMAIL_SCREEN_ROUTE, EMAIL_ARG) {
        fun getRouteWithArgs(email: String) = route.appendParams(
            EMAIL_ARG to email
        )
    }


    //Note feature
    object Notes: Screen(NOTES_SCREEN_ROUTE)

    object AddEditNote: Screen(ADD_EDIT_NOTE_SCREEN_ROUTE)


    companion object {

        //Authentication feature
        private const val LOGIN_SCREEN_ROUTE = "login"
        private const val REGISTER_SCREEN_ROUTE = "register"
        private const val RESET_PASSWORD_SCREEN_ROUTE = "reset_password"
        private const val CONFIRM_PASSWORD_RESET_SCREEN_ROUTE = "confirm_password_reset"
        private const val VERIFY_EMAIL_SCREEN_ROUTE = "confirm_email"

        const val EMAIL_ARG = "email_arg"

        //Note feature
        private const val NOTES_SCREEN_ROUTE = "notes"
        private const val ADD_EDIT_NOTE_SCREEN_ROUTE = "notes"

    }


    internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
        val builder = StringBuilder(this)

        params.forEach {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }

}
