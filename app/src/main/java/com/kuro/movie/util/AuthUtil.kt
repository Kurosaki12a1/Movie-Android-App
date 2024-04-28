package com.kuro.movie.util

data class StandardTextFieldState(
    val text: String = "",
    val error: AuthError? = null
)

sealed class AuthError {
    data object FieldEmpty : AuthError()
}

