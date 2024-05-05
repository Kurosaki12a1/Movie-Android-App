package com.kuro.movie.data.mapper

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.entity.movie.FavoriteMovie
import com.kuro.movie.data.model.entity.movie.MovieWatchListItem
import com.kuro.movie.domain.model.UpComingMovie
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

fun MovieResponse.toUpComingMovie(): UpComingMovie {
    return UpComingMovie(
        movie = this.toMovie(),
        isAddedToRemind = false
    )
}

fun Movie.toFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        movieId = this.id,
        movie = this
    )
}

fun Movie.toMovieWatchListItem(): MovieWatchListItem {
    return MovieWatchListItem(
        movieId = this.id,
        movie = this
    )
}