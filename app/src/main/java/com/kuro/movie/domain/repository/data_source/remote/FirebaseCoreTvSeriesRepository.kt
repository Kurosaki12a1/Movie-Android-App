package com.kuro.movie.domain.repository.data_source.remote

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.util.Resource

interface FirebaseCoreTvSeriesRepository {
    suspend fun addTvSeriesToFavoriteList(
        userUid: String,
        tvSeriesInFavoriteList: List<TvSeries>
    ): Resource<Unit>

    suspend fun addTvSeriesToWatchList(
        userUid: String,
        tvSeriesInWatchList: List<TvSeries>
    ): Resource<Unit>
}