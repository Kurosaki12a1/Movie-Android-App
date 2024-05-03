package com.kuro.movie.domain.repository

import com.kuro.movie.data.model.GenreList
import com.kuro.movie.domain.response.GenreListResponse
import com.kuro.movie.util.Constants
import io.reactivex.Observable

interface GenreRepository {
    suspend fun getMovieGenreList(
        language: String = Constants.DEFAULT_LANGUAGE
    ): GenreList

    suspend fun getTvGenreList(
        language: String = Constants.DEFAULT_LANGUAGE
    ): GenreList
}