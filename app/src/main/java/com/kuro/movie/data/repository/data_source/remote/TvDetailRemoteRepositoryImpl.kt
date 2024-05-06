package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.DetailAPI
import com.kuro.movie.domain.repository.data_source.remote.TvDetailRemoteRepository
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.domain.response.detail.tv.TvDetailResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvDetailRemoteRepositoryImpl @Inject constructor(
    private val detailAPI: DetailAPI
) : TvDetailRemoteRepository {
    override suspend fun getTvDetail(tvSeriesId: Int, language: String): TvDetailResponse {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                detailAPI.getTvDetail(tvSeriesId, language)
            }
        }
    }

    override suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int
    ): ApiResponse<TvSeriesResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                detailAPI.getRecommendationsForTv(tvSeriesId, language, page)
            }
        }
    }

    override suspend fun getTvVideos(tvSeriesId: Int, language: String): VideosResponse {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                detailAPI.getTvVideos(tvSeriesId, language)
            }
        }
    }
}