package com.kuro.movie.domain.repository

import androidx.paging.PagingData
import com.kuro.movie.data.model.Movie
import com.kuro.movie.util.Constants.DEFAULT_REGION
import io.reactivex.Observable

interface HomeMovieRepository {
    fun getNowPlayingMovies(
        language: String,
        region: String = DEFAULT_REGION
    ): Observable<PagingData<Movie>>

    fun getPopularMovies(
        language: String,
        region: String
    ): Observable<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String,
        region: String
    ): Observable<PagingData<Movie>>
}