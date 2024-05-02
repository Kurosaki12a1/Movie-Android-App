package com.kuro.movie.domain.repository.firebase

import com.kuro.movie.data.model.Movie
import com.kuro.movie.util.Resource

interface FirebaseCoreMovieRepository {
    suspend fun addMovieToFavoriteList(
        userUid: String,
        movieInFavoriteList: List<Movie>
    ): Resource<Unit>

    suspend fun addMovieToWatchList(
        userUid: String,
        moviesInWatchList: List<Movie>
    ): Resource<Unit>
}