package com.kuro.movie.util

import androidx.paging.PagingData
import androidx.paging.map
import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.Movie
import io.reactivex.Observable

fun combineMovieAndGenreReturnObservable(
    listGenre: List<Genre>,
    moviePagingDataObservable: Observable<PagingData<Movie>>
): Observable<PagingData<Movie>> {
    return moviePagingDataObservable.map { moviePagingData ->
        moviePagingData.map { movie ->
            movie.copy(
                genresBySeparatedByComma = GenreDomainUtils.getGenresBySeparatedByComma(
                    movie.genreIds,
                    listGenre
                )
            )
        }
    }
}

fun combineMovieAndGenreMapOneGenre(
    listGenre: List<Genre>,
    moviePagingDataObservable: Observable<PagingData<Movie>>
): Observable<PagingData<Movie>> {
    return moviePagingDataObservable.map { moviePagingData ->
        moviePagingData.map { movie ->
            movie.copy(
                genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                    genreList = listGenre,
                    genreIds = movie.genreIds
                )
            )
        }
    }
}