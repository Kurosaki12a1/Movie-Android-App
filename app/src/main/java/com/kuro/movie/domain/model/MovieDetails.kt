package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.Movie

data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val imdbId: String?,
    val originalTitle: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val formattedVoteCount: String,
    var convertedRuntime: Map<String, String> = emptyMap(),
    val credit: Credit?,
    var ratingValue: Float = 0f,
    val isFavorite: Boolean = false,
    val isWatchList: Boolean = false,
    val watchProviders: WatchProviderItem?,
    val genresBySeparatedByComma: String = "",
)

fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview ?: "",
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genres.map { it.id },
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}