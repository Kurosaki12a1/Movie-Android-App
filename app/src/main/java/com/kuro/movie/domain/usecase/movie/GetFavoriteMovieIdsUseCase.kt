package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavoriteMovieIdsUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(): List<Int> {
        return repository.getFavoriteMovieIds()
    }
}