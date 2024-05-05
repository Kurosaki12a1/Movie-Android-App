package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.UpComingApiResponse

interface UpComingMovieRemoteRepository {
    suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieResponse>
}