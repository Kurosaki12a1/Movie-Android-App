package com.kuro.movie.domain.usecase.tvseries

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavoriteTvSeriesUseCase @Inject constructor(
    private val repository: TvSeriesLocalRepository
) {

    suspend operator fun invoke(): List<TvSeries> {
        return repository.getFavoriteTvSeries()
    }
}