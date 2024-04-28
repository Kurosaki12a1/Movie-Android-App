package com.kuro.movie.domain.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int?,
    val adult: Boolean?,
    val overview: String?,
    val title: String?,
    val popularity: Double?,
    val video: Boolean?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("vote_average") val voteAverage: Double?
)