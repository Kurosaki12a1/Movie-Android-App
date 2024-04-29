package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(private val repository: HomeMovieRemoteRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getPopularMovies(language, region, page)
}