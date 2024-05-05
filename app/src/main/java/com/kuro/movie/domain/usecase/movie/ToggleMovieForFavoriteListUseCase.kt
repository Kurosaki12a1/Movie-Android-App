package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import javax.inject.Inject

class ToggleMovieForFavoriteListUseCase @Inject constructor(
    private val repository: MovieLocalRepository,
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddFavoriteList: Boolean,
    ) {
        if (doesAddFavoriteList) {
            repository.deleteMovieFromFavoriteList(
                movie = movie
            )
        } else {
            repository.insertMovieToFavoriteList(
                movie = movie
            )
        }
    }
}