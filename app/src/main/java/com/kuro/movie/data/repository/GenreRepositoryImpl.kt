package com.kuro.movie.data.repository

import com.kuro.movie.data.data_source.remote.GenreAPI
import com.kuro.movie.data.mapper.toGenreList
import com.kuro.movie.data.model.GenreList
import com.kuro.movie.domain.repository.GenreRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreAPI: GenreAPI,
) : GenreRepository {
    override suspend fun getMovieGenreList(language: String): Observable<GenreList> {
        return genreAPI.getMovieGenreList(language = language)
            .map { response -> response.toGenreList() }
            .subscribeOn(Schedulers.io()).onErrorReturn {
                it.printStackTrace()
                GenreList(emptyList())
            }
    }

    override suspend fun getTvGenreList(language: String): Observable<GenreList> {
        return genreAPI.getTvGenreList(language = language)
            .map { response -> response.toGenreList() }
            .subscribeOn(Schedulers.io()).onErrorReturn {
                it.printStackTrace()
                GenreList(emptyList())
            }
    }
}