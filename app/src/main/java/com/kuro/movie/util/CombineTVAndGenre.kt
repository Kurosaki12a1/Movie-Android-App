package com.kuro.movie.util

import androidx.paging.PagingData
import androidx.paging.map
import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.TvSeries
import io.reactivex.Observable

fun combineTvAndGenreMapOneGenre(
    tvGenreResourceObservable: Observable<List<Genre>>,
    tvPagingDataObservable: Observable<PagingData<TvSeries>>
): Observable<PagingData<TvSeries>> {
    return Observable.combineLatest(
        tvPagingDataObservable,
        tvGenreResourceObservable
    ) { tvPagingData, tvGenreList ->
        tvPagingData.map { tv ->
            tv.copy(
                genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                    genreList = tvGenreList,
                    genreIds = tv.genreIds
                )
            )
        }
    }
}