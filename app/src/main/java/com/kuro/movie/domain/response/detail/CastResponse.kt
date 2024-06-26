package com.kuro.movie.domain.response.detail

import com.google.gson.annotations.SerializedName

data class CastResponse(
    val id: Int?,
    val adult: Boolean?,
    val gender: Int?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    val name: String?,
    @SerializedName("original_name") val originalName: String?,
    val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?,
    val character: String?,
    @SerializedName("credit_id") val creditId: String?,
    val order: Int?
)