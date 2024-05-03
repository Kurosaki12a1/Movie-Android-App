package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse

interface ExploreMovieRemoteRepository {

    suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieResponse>
}