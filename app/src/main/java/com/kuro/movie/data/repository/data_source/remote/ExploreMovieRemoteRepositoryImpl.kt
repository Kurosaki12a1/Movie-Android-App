package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.ExploreAPI
import com.kuro.movie.domain.repository.data_source.remote.ExploreMovieRemoteRepository
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreMovieRemoteRepositoryImpl @Inject constructor(
    private val exploreApi: ExploreAPI,
) : ExploreMovieRemoteRepository {
    override suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall { exploreApi.discoverMovie(page, language, genres, sort) }
        }
    }
}