package com.kuro.movie.data.mapper

import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.util.Utils
import com.kuro.movie.extension.orZero

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id.orZero(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        posterPath = posterPath,
        releaseDate = Utils.convertToYearFromDate(releaseDate),
        genreIds = genreIds.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount),
        fullReleaseDate = releaseDate
    )
}