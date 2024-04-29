package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import javax.inject.Inject

class GetTopRatedMovieUseCase @Inject constructor(private val repository: HomeMovieRemoteRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getTopRatedMovies(language, region, page)
}