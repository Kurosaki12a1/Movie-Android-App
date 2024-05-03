package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.TvSeriesResponse

interface ExploreTvRemoteRepository {

    suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesResponse>
}