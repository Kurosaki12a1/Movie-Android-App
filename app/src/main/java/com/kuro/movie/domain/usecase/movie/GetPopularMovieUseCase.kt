package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.HomeMovieRepository
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(private val repository: HomeMovieRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getPopularMovies(language, region, page)
}