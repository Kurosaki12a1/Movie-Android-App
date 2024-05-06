package com.kuro.movie.data.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.domain.response.detail.movie.MovieDetailResponse
import com.kuro.movie.domain.response.detail.tv.TvDetailResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse
import com.kuro.movie.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailAPI {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): Response<MovieDetailResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): Response<TvDetailResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsForMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<ApiResponse<MovieResponse>>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendationsForTv(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<ApiResponse<TvSeriesResponse>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): Response<VideosResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvVideos(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String
    ): Response<VideosResponse>
}