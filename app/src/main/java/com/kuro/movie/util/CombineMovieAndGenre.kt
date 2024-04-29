package com.kuro.movie.util

import androidx.paging.PagingData
import androidx.paging.map
import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.Movie
import io.reactivex.Observable

fun combineMovieAndGenreReturnObservable(
    movieGenreResourceObservable: Observable<List<Genre>>,
    moviePagingDataObservable: Observable<PagingData<Movie>>
): Observable<PagingData<Movie>> {
    return Observable.combineLatest(
        moviePagingDataObservable,
        movieGenreResourceObservable
    ) { moviePagingData, movieGenreList ->
        moviePagingData.map { movie ->
            movie.copy(
                genresBySeparatedByComma = GenreDomainUtils.getGenresBySeparatedByComma(
                    movie.genreIds,
                    movieGenreList
                )
            )
        }
    }
}

fun combineMovieAndGenreMapOneGenre(
    movieGenreResourceObservable: Observable<List<Genre>>,
    moviePagingDataObservable: Observable<PagingData<Movie>>
): Observable<PagingData<Movie>> {
    return Observable.combineLatest(
        moviePagingDataObservable,
        movieGenreResourceObservable
    ) { moviePagingData, movieGenreList ->
        moviePagingData.map { movie ->
            movie.copy(
                genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                    genreList = movieGenreList,
                    genreIds = movie.genreIds
                )
            )
        }
    }
}