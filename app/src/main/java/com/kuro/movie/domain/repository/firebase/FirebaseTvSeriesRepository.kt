package com.kuro.movie.domain.repository.firebase

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.util.Resource

interface FirebaseTvSeriesRepository {

    suspend fun getFavoriteTvSeries(userUid: String): Resource<List<TvSeries>>

    suspend fun getTvSeriesInWatchList(userUid: String): Resource<List<TvSeries>>
}