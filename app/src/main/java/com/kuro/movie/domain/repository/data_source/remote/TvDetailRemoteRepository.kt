package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.domain.response.detail.tv.TvDetailResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse

interface TvDetailRemoteRepository {

    suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String
    ): TvDetailResponse

    suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int = 1
    ): ApiResponse<TvSeriesResponse>

    suspend fun getTvVideos(
        tvSeriesId: Int,
        language: String
    ): VideosResponse
}