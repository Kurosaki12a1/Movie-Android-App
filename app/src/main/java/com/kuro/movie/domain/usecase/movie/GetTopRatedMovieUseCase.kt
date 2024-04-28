package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.HomeMovieRepository
import javax.inject.Inject

class GetTopRatedMovieUseCase @Inject constructor(private val repository: HomeMovieRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getTopRatedMovies(language, region, page)
}