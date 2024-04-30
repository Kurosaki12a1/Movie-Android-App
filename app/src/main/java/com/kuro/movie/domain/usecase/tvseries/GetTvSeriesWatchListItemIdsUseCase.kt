package com.kuro.movie.domain.usecase.tvseries

import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTvSeriesWatchListItemIdsUseCase @Inject constructor(
    private val repository: TvSeriesLocalRepository
) {
    operator fun invoke(): Flowable<List<Int>> {
        return repository.getTvSeriesWatchListItemIds()
    }
}