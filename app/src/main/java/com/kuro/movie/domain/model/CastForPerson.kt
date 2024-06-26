package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

data class CastForPerson(
    val id: Int,
    val name: String?,
    val originalName: String?,
    val originalTitle: String?,
    val character: String,
    val firstAirDate: String?,
    val mediaType: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val title: String?,
    val popularity: Double
)


fun CastForPerson.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        title = title ?: "",
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = emptyList(),
        voteAverage = voteAverage
    )
}

fun CastForPerson.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        overview = overview,
        posterPath = posterPath,
        genreIds = emptyList(),
        voteAverage = voteAverage,
        name = name ?: "",
        firstAirDate = firstAirDate
    )
}