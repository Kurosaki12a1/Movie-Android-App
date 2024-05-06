package com.kuro.movie.domain.response.detail.tv

import com.google.gson.annotations.SerializedName


data class CreatedByResponse(
    @SerializedName("credit_id") val creditId: String?,
    val gender: Int?,
    val id: Int?,
    val name: String?,
    @SerializedName("profile_path") val profilePath: String?
)