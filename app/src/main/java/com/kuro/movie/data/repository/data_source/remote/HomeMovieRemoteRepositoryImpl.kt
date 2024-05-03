package com.kuro.movie.data.repository.data_source.remote


import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.domain.response.ApiResponse
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeMovieRemoteRepositoryImpl @Inject constructor(
    private val homeAPI: HomeAPI
) : HomeMovieRemoteRepository {

    override suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                homeAPI.getNowPlayingMovie(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }

    override suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                homeAPI.getPopularMovies(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }

    override suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieResponse> {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                homeAPI.getTopRatedMovies(
                    language = language,
                    page = page,
                    region = region
                )
            }
        }
    }
}