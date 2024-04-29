package com.kuro.movie.domain.repository.data_source.remote


import com.kuro.movie.data.model.ApiResponse
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.util.Resource
import io.reactivex.Observable

interface HomeMovieRemoteRepository {

    suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse>

    suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse>

    suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse>
}