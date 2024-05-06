package com.kuro.movie.domain.usecase.detail.tv

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.detail.TvDetailRepository
import com.kuro.movie.domain.usecase.tvseries.GetTvGenreListUseCase
import com.kuro.movie.util.GenreDomainUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTvRecommendationUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    suspend operator fun invoke(tvId: Int, language: String): List<TvSeries> {
        return withContext(Dispatchers.IO) {
            val tvSeries = async {
                tvDetailRepository.getRecommendationsForTv(
                    tvId, language
                )
            }
            val genres = async { getTvGenreListUseCase(language) }

            tvSeries.await().map { tv ->
                tv.copy(
                    genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres.await(),
                        genreIds = tv.genreIds
                    )
                )
            }
        }
    }
}