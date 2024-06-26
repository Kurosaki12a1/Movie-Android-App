package com.kuro.movie.domain.usecase.tvseries

import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavoriteTvSeriesIdsUseCase @Inject constructor(
    private val repository: TvSeriesLocalRepository
) {
    suspend operator fun invoke(): List<Int> {
        return repository.getFavoriteTvSeriesIds()
    }
}