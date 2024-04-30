package com.kuro.movie.domain.usecase.tvseries

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import javax.inject.Inject

class ToggleTvSeriesForWatchListItemUseCase @Inject constructor(
    private val repository: TvSeriesLocalRepository
) {
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddWatchList: Boolean,
    ) {
        if (doesAddWatchList) {
            repository.deleteTvSeriesFromWatchListItem(tvSeries = tvSeries)
        } else {
            repository.insertTvSeriesToWatchListItem(tvSeries = tvSeries)
        }
    }
}