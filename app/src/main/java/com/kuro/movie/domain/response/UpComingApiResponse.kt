package com.kuro.movie.domain.response

import com.google.gson.annotations.SerializedName

data class UpComingApiResponse<T>(
    val dates: Dates,
    val page: Int,
    val results: List<T>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)

data class Dates(
    val maximum: String,
    val minimum: String
)