package com.kuro.movie.domain.usecase.detail.tv

import com.kuro.movie.data.mapper.filterTrailerOrTeaserSortedByDescending
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.repository.detail.TvDetailRepository
import com.kuro.movie.util.Resource
import com.kuro.movie.util.handleResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTvVideosUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository
) {
    suspend operator fun invoke(tvId: Int, language: String): Resource<Videos> {
        return withContext(Dispatchers.IO) {
            handleResource(
                resourceSupplier = {
                    tvDetailRepository.getTvVideos(
                        tvSeriesId = tvId,
                        language = language.lowercase()
                    )
                },
                mapper = { videos ->
                    videos.filterTrailerOrTeaserSortedByDescending()
                }
            )
        }
    }
}