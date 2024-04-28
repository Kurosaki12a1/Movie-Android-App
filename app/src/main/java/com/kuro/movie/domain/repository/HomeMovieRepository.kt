package com.kuro.movie.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import com.kuro.movie.data.model.Movie

interface HomeMovieRepository {

    suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): Observable<Movie>

    suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): Single<Movie>

    suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): Single<Movie>
}