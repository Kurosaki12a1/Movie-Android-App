package com.kuro.movie.extension

import com.google.android.material.textfield.TextInputLayout
import com.kuro.movie.R
import com.kuro.movie.util.AuthError

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun TextInputLayout.updateFieldEmptyError(
    authError: AuthError?
) {
    isErrorEnabled = authError != null
    when (authError) {
        is AuthError.FieldEmpty -> {
            error = this.context.getString(R.string.error_field_empty)
        }

        null -> return
    }
}

