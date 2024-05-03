package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.ExploreAPI
import com.kuro.movie.domain.repository.data_source.remote.ExploreMultiSearchRemoteRepository
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.SearchResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreMultiSearchRepositoryImpl @Inject constructor(
    private val exploreApi: ExploreAPI,
) : ExploreMultiSearchRemoteRepository {
    override suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int
    ): ApiResponse<SearchResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall { exploreApi.multiSearch(query, language, page) }
        }
    }
}