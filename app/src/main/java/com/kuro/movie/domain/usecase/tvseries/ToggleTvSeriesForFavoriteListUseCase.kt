package com.kuro.movie.domain.usecase.tvseries

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import javax.inject.Inject

class ToggleTvSeriesForFavoriteListUseCase @Inject constructor(
    private val repository: TvSeriesLocalRepository,
) {
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddFavoriteList: Boolean,
    ) {
        if (doesAddFavoriteList) {
            repository.deleteTvSeriesFromFavoriteList(tvSeries = tvSeries)
        } else {
            repository.insertTvSeriesToFavoriteList(tvSeries = tvSeries)
        }
    }
}