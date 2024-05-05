package com.kuro.movie.data.data_source.remote

import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.UpComingApiResponse
import com.kuro.movie.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UpComingAPI {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String
    ): Response<UpComingApiResponse<MovieResponse>>
}