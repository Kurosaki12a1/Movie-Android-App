package com.kuro.movie.domain.repository.firebase

import com.kuro.movie.data.model.Movie
import com.kuro.movie.util.Resource

interface FirebaseMovieRepository {

    suspend fun getFavoriteMovie(
        userUid: String
    ): Resource<List<Movie>>

    suspend fun getMovieInWatchList(
        userUid: String
    ): Resource<List<Movie>>
}