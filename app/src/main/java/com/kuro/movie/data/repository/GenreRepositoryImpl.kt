package com.kuro.movie.data.repository

import com.kuro.movie.data.data_source.remote.GenreAPI
import com.kuro.movie.data.mapper.toGenreList
import com.kuro.movie.data.model.GenreList
import com.kuro.movie.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreAPI: GenreAPI,
) : GenreRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {
        return genreAPI.getMovieGenreList(language = language).toGenreList()
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return genreAPI.getTvGenreList(language = language).toGenreList()
    }
}