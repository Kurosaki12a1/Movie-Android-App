package com.kuro.movie.util

class EmailEmptyException(
    override val message: String = "Email field is empty."
) : Exception(message)

class PasswordEmptyException(
    override val message: String = "Password field is empty."
) : Exception(message)