package com.kuro.movie.data.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.SearchResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreAPI {
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): Response<ApiResponse<MovieResponse>>


    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): Response<ApiResponse<TvSeriesResponse>>


    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int = Constants.STARTING_PAGE
    ): Response<ApiResponse<SearchResponse>>
}