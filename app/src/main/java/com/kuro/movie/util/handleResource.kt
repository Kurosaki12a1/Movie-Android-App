package com.kuro.movie.util

inline fun <T> handleResource(
    resourceSupplier: () -> Resource<T>,
    mapper: (T) -> T = { it }
): Resource<T> {
    return when (val resource = resourceSupplier()) {
        is Resource.Success -> {
            resource.value?.let {
                val result = mapper(it)
                Resource.Success(result)
            } ?: Resource.failure(Exception("Unknown error"))
        }

        is Resource.Failure -> {
            Resource.failure(Exception("Unknown error"))
        }

        is Resource.Loading -> Resource.Loading
    }
}