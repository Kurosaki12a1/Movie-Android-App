package com.kuro.movie.domain.response.detail.tv

import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    val id: Int?,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String?,
    @SerializedName("origin_country") val originCountry: String?
)