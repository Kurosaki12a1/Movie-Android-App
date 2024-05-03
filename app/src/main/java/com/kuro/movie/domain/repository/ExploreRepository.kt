package com.kuro.movie.domain.repository

import androidx.paging.PagingData
import com.kuro.movie.domain.model.FilterBottomState
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.data.model.TvSeries
import io.reactivex.Observable

interface ExploreRepository {

    fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<Movie>>


    fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<TvSeries>>

    fun multiSearch(
        query: String,
        language: String
    ): Observable<PagingData<MultiSearch>>
}