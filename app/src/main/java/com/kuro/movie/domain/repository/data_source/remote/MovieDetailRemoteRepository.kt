package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.detail.movie.MovieDetailResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse

interface MovieDetailRemoteRepository {

    suspend fun getMovieDetail(
        movieId: Int,
        language: String
    ): MovieDetailResponse

    suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int = 1
    ): ApiResponse<MovieResponse>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String
    ): VideosResponse
}