package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.DetailAPI
import com.kuro.movie.domain.repository.data_source.remote.MovieDetailRemoteRepository
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.detail.movie.MovieDetailResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailRemoteRepositoryImpl @Inject constructor(
    private val detailAPI: DetailAPI
) : MovieDetailRemoteRepository {
    override suspend fun getMovieDetail(movieId: Int, language: String): MovieDetailResponse {
        return withContext(Dispatchers.IO) {
            tryApiCall { detailAPI.getMovieDetail(movieId, language) }
        }
    }

    override suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int
    ): ApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall { detailAPI.getRecommendationsForMovie(movieId, language, page) }
        }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): VideosResponse {
        return withContext(Dispatchers.IO) {
            tryApiCall { detailAPI.getMovieVideos(movieId, language) }
        }
    }
}