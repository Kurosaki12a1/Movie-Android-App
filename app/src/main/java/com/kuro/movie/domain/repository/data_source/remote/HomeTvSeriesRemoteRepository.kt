package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.data.model.ApiResponse
import com.kuro.movie.domain.response.TvSeriesResponse

interface HomeTvSeriesRemoteRepository {

    suspend fun getPopularTvs(
        language: String,
        page: Int
    ): ApiResponse<TvSeriesResponse>

    suspend fun getTopRatedTvs(
        language: String,
        page: Int
    ): ApiResponse<TvSeriesResponse>
}