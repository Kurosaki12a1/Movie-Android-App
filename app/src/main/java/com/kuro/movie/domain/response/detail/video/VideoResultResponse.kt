package com.kuro.movie.domain.response.detail.video

import com.google.gson.annotations.SerializedName

data class VideoResultResponse(
    val id: String?,
    val iso_3166_1: String?,
    val iso_639_1: String?,
    val key: String?,
    val name: String?,
    val official: Boolean?,
    @SerializedName("published_at") val publishedAt: String?,
    val site: String?,
    val size: Int?,
    val type: String?,
)