package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.repository.data_source.remote.HomeTvSeriesRemoteRepository
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeTvSeriesRemoteRepositoryImpl @Inject constructor(
    private val homeAPI: HomeAPI
) : HomeTvSeriesRemoteRepository {

    override suspend fun getPopularTvs(language: String, page: Int): ApiResponse<TvSeriesResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                homeAPI.getPopularTvSeries(
                    language = language,
                    page = page
                )
            }
        }
    }

    override suspend fun getTopRatedTvs(
        language: String,
        page: Int
    ): ApiResponse<TvSeriesResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                homeAPI.getTopRatedTvSeries(
                    language = language,
                    page = page
                )
            }
        }
    }
}