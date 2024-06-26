package com.kuro.movie.util

sealed class Resource<out T> {

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)

        fun failure(
            error: Throwable,
            errorMessage : String? = null,
        ): Resource<Nothing> = Failure(error, errorMessage)
    }

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val error: Throwable,
        val errorMessage : String? = null,
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}