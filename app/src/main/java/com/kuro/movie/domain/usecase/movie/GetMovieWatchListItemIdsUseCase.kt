package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetMovieWatchListItemIdsUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    operator fun invoke(): Flowable<List<Int>> {
        return repository.getMovieWatchListItemIds()
    }
}