package com.kuro.movie.util

import retrofit2.Response

inline fun <reified R> tryApiCall(
    block: () -> Response<R>
): R {

    val result = try {
        val response = block()
        if (response.isSuccessful) {
            response
        } else {
            throw ServiceUnavailableException()
        }
    } catch (e: Exception) {
        throw ServiceUnavailableException()
    }

    when (result.code()) {
        in 200..299 -> Unit
        500 -> throw ServiceUnavailableException()
        in 400..499 -> throw ServiceUnavailableException()
        else -> throw ServiceUnavailableException()
    }

    return try {
        result.body() ?: throw Exception()
    } catch (e: Exception) {
        throw ServiceUnavailableException()
    }
}

class ServiceUnavailableException : Exception() {
    override val message: String
        get() = "Oops, something went wrong. Please check your internet connection and try again later."
}

suspend inline fun <T : Any> safeApiCallReturnResource(
    crossinline apiCall: suspend () -> T,
): Resource<T> {
    return try {
        val response = apiCall.invoke()
        Resource.Success(response)
    } catch (e: Exception) {
        Resource.failure(e)
    }
}
