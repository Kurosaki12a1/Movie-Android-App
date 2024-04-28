package com.kuro.movie.data.mapper

import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.response.TvSeriesResponse
import com.kuro.movie.util.Utils
import com.kuro.movie.extension.orZero

fun TvSeriesResponse.toTvSeries(): TvSeries {
    return TvSeries(
        id = id.orZero(),
        overview = overview.orEmpty(),
        name = name.orEmpty(),
        posterPath = posterPath,
        firstAirDate = Utils.convertToYearFromDate(firstAirDate),
        genreIds = genreIds.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount),
    )
}