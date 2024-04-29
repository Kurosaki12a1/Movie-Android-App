package com.kuro.movie.util

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestoreException
import com.kuro.movie.R

class FirebaseFireStoreErrorMessage {

    companion object {

        fun getMessage(
            errorCode: String
        ): Int {
            var errorStringId = R.string.unknown_error
            errorMessages.forEach { (t, u) ->
                if (errorCode == t) {
                    errorStringId = u
                }
            }
            return errorStringId
        }

        private val errorMessages = mapOf(
            "PERMISSION_DENIED" to R.string.permission_denied_error,
            "NOT_FOUND" to R.string.not_found_error,
            "ALREADY_EXISTS" to R.string.already_exists_error,
            "ABORTED" to R.string.aborted_error,
            "UNAVAILABLE" to R.string.unavailable_error
        )

        fun <T> setExceptionToFirebaseMessage(
            context: Context,
            exception: Exception
        ): Resource<T> {
            exception.printStackTrace()
            return if (exception is FirebaseFirestoreException) {
                val errorCode = exception.code.toString()
                val errorStringId = getMessage(errorCode = errorCode)
                Resource.failure(Exception(context.getString(errorStringId)))
            } else {
                Resource.failure(Exception(context.getString(R.string.unknown_error)))
            }
        }
    }
}