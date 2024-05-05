package com.kuro.movie.data.repository.data_source.remote

import com.kuro.movie.data.data_source.remote.UpComingAPI
import com.kuro.movie.domain.repository.data_source.remote.UpComingMovieRemoteRepository
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.UpComingApiResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpComingMovieRemoteRepositoryImpl @Inject constructor(
    private val upComingApi: UpComingAPI,
) : UpComingMovieRemoteRepository {
    override suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                upComingApi.getUpComingMovies(
                    page = page,
                    language = language
                )
            }
        }
    }
}