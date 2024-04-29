package com.kuro.movie.domain.usecase.tvseries

import androidx.paging.PagingData
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.util.Constants.DEFAULT_LANGUAGE
import com.kuro.movie.util.combineTvAndGenreMapOneGenre
import io.reactivex.Observable
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val homeTvRepository: HomeTvRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    suspend operator fun invoke(language: String = DEFAULT_LANGUAGE): Observable<PagingData<TvSeries>> {
        return combineTvAndGenreMapOneGenre(
            tvGenreResourceObservable = getTvGenreListUseCase(language = language),
            tvPagingDataObservable = homeTvRepository.getTopRatedTvs(language = language)
        )
    }
}