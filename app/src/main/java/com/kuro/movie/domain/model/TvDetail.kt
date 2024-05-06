package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.TvSeries

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    val firstAirDate: String,
    val createdBy: List<CreatedBy>?,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val originalName: String,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val status: String,
    val voteAverage: Double,
    val formattedVoteCount: String,
    var ratingValue: Float = 0f,
    var releaseDate: String = "",
    val credit: Credit?,
    val isFavorite: Boolean = false,
    val isWatchList: Boolean = false,
    val watchProviders: WatchProviderItem?,
    val genresBySeparatedByComma: String = "",
)

fun TvDetail.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        overview = overview,
        name = name,
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        genreIds = genres.map { it.id },
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}

data class CreatedBy(
    val id: Int,
    val name: String
)