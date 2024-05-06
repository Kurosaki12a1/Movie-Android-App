package com.kuro.movie.domain.usecase.detail.tv

import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.repository.detail.TvDetailRepository
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesIdsUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesWatchListItemIdsUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Resource
import com.kuro.movie.util.handleResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
    private val getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase
) {

    suspend operator fun invoke(
        language: String,
        tvId: Int
    ): Resource<TvDetail> {
        return withContext(Dispatchers.IO) {
            handleResource(
                resourceSupplier = {
                    tvDetailRepository.getTvDetail(
                        language = language,
                        tvSeriesId = tvId,
                        countryIsoCode = Constants.DEFAULT_LANGUAGE
                    )
                },
                mapper = { tvDetail ->
                    tvDetail.copy(
                        isFavorite = getFavoriteTvSeriesIdsUseCase().contains(tvDetail.id),
                        isWatchList = getTvSeriesWatchListItemIdsUseCase().contains(tvDetail.id)
                    )
                })
        }
    }
}