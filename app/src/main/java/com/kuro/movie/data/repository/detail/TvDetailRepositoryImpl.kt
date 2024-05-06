package com.kuro.movie.data.repository.detail

import com.kuro.movie.data.mapper.toTvDetail
import com.kuro.movie.data.mapper.toTvSeries
import com.kuro.movie.data.mapper.toVideo
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.repository.data_source.remote.TvDetailRemoteRepository
import com.kuro.movie.domain.repository.detail.TvDetailRepository
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeApiCallReturnResource
import javax.inject.Inject

class TvDetailRepositoryImpl @Inject constructor(
    private val repository: TvDetailRemoteRepository
) : TvDetailRepository {
    override suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<TvDetail> {
        return safeApiCallReturnResource {
            repository.getTvDetail(tvSeriesId, language).toTvDetail(countryIsoCode)
        }
    }

    override suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int
    ): List<TvSeries> {
        return repository.getRecommendationsForTv(
            tvSeriesId,
            language,
            page
        ).results.map { it.toTvSeries() }
    }

    override suspend fun getTvVideos(tvSeriesId: Int, language: String): Resource<Videos> {
        return safeApiCallReturnResource {
            repository.getTvVideos(tvSeriesId, language).toVideo()
        }
    }
}