package com.kuro.movie.domain.response.detail.video

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    val id: Int?,
    @SerializedName("results") val results: List<VideoResultResponse>?
)
