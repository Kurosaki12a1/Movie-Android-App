package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import javax.inject.Inject

class GetNowPlayingMovieUseCase @Inject constructor(private val repository: HomeMovieRemoteRepository) {

    suspend fun invoke(
        language: String,
        region: String,
        page: Int
    ) = repository.getNowPlayingMovies(language, region, page)

}