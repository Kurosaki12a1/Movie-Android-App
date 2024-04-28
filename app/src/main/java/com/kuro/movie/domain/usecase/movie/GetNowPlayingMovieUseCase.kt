package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.HomeMovieRepository
import javax.inject.Inject

class GetNowPlayingMovieUseCase @Inject constructor(private val repository: HomeMovieRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getNowPlayingMovies(language, region, page)

}