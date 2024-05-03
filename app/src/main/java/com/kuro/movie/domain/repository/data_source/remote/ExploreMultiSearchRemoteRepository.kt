package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.SearchResponse
import com.kuro.movie.util.Constants

interface ExploreMultiSearchRemoteRepository {

    suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int = Constants.STARTING_PAGE
    ): ApiResponse<SearchResponse>
}