package com.kuro.movie.domain.response.detail.tv

import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_count") val episodeCount: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("season_number") val seasonNumber: Int?
)