package com.kuro.movie.data.data_source.remote


import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeAPI {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieResponse>>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieResponse>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieResponse>>


    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): Response<ApiResponse<TvSeriesResponse>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvSeries(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): Response<ApiResponse<TvSeriesResponse>>

}