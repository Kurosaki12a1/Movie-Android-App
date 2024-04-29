package com.kuro.movie.domain.repository.data_source.local

import com.kuro.movie.data.model.TvSeries
import io.reactivex.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface TvSeriesLocalRepository {
    suspend fun insertTvSeriesToFavoriteList(tvSeries: TvSeries)

    suspend fun insertTvSeriesToWatchListItem(tvSeries: TvSeries)

    suspend fun deleteTvSeriesFromFavoriteList(tvSeries: TvSeries)

    suspend fun deleteTvSeriesFromWatchListItem(tvSeries: TvSeries)

    fun getFavoriteTvSeriesIds(): Flowable<List<Int>>

    fun getTvSeriesWatchListItemIds(): Flowable<List<Int>>

    fun getFavoriteTvSeries(): Flowable<List<TvSeries>>

    fun getTvSeriesInWatchList(): Flowable<List<TvSeries>>
}