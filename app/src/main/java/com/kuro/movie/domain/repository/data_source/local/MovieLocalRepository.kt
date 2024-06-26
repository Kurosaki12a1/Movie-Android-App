package com.kuro.movie.domain.repository.data_source.local

import com.kuro.movie.data.model.Movie
import io.reactivex.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    suspend fun insertMovieToFavoriteList(movie: Movie)

    suspend fun insertMovieToWatchList(movie: Movie)

    suspend fun deleteMovieFromFavoriteList(movie: Movie)

    suspend fun deleteMovieFromWatchListItem(movie: Movie)

    suspend fun getFavoriteMovieIds(): List<Int>

    suspend fun getMovieWatchListItemIds(): List<Int>

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun getMoviesInWatchList(): List<Movie>

}