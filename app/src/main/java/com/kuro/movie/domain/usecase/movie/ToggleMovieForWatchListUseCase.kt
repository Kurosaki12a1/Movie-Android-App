package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import javax.inject.Inject

class ToggleMovieForWatchListUseCase @Inject constructor(
    private val repository: MovieLocalRepository,
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddWatchList: Boolean,
    ) {
        if (doesAddWatchList) {
            repository.deleteMovieFromWatchListItem(movie = movie)
        } else {
            repository.insertMovieToWatchList(movie = movie)
        }
    }
}