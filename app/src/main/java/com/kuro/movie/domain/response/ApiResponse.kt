package com.kuro.movie.domain.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val page: Int,
    val results: List<T>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)
