package com.kuro.movie.domain.usecase.tvseries

import androidx.paging.PagingData
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.FilterBottomState
import com.kuro.movie.domain.repository.ExploreRepository
import com.kuro.movie.util.Constants
import com.kuro.movie.util.combineTvAndGenreMapOneGenre
import io.reactivex.Observable
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val tvGenreListUseCase: GetTvGenreListUseCase
) {
    suspend operator fun invoke(
        language: String = Constants.DEFAULT_LANGUAGE,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<TvSeries>> {
        return combineTvAndGenreMapOneGenre(
            listTvGenre = tvGenreListUseCase(language = language),
            tvPagingDataObservable = exploreRepository.discoverTv(
                language = language,
                filterBottomState = filterBottomState
            )
        )
    }
}