package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.ExploreAPI
import com.kuro.movie.domain.repository.data_source.remote.ExploreTvRemoteRepository
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreTvRemoteRepositoryImpl @Inject constructor(
    private val exploreApi: ExploreAPI,
) : ExploreTvRemoteRepository {

    override suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall { exploreApi.discoverTv(page, language, genres, sort) }
        }
    }
}