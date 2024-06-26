package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

data class CrewForPerson(
    val id: Int,
    val department: String,
    val firstAirDate: String?,
    val job: String,
    val mediaType: String,
    val name: String?,
    val popularity: Double,
    val title: String?,
    val originalName: String?,
    val originalTitle: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int
)

fun CrewForPerson.toMovie(): Movie {
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

fun CrewForPerson.toTvSeries(): TvSeries {
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