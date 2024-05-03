package com.kuro.movie.domain.usecase.movie

import com.kuro.movie.data.model.Genre
import com.kuro.movie.domain.repository.GenreRepository
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: GenreRepository
) {

    suspend operator fun invoke(language: String): List<Genre> {
        return repository.getMovieGenreList(language).genres
    }
}