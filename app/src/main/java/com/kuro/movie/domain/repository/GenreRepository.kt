package com.kuro.movie.domain.repository

import com.kuro.movie.data.model.GenreList
import io.reactivex.Observable

interface GenreRepository {
    suspend fun getMovieGenreList(
        language: String
    ): Observable<GenreList>

    suspend fun getTvGenreList(
        language: String
    ): Observable<GenreList>
}