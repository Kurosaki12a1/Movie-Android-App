package com.kuro.movie.domain.repository

import androidx.paging.PagingData
import com.kuro.movie.data.model.TvSeries
import io.reactivex.Observable

interface HomeTvRepository {

    fun getPopularTvs(
        language: String
    ): Observable<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Observable<PagingData<TvSeries>>
}