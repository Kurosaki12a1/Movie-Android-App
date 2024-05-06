package com.kuro.movie.domain.repository.detail

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.util.Resource

interface TvDetailRepository {
    suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<TvDetail>

    suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int = 1
    ): List<TvSeries>

    suspend fun getTvVideos(
        tvSeriesId: Int,
        language: String
    ): Resource<Videos>
}