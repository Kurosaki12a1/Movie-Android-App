package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.response.detail.tv.TvDetailResponse
import com.kuro.movie.extension.orZero
import com.kuro.movie.util.Utils

fun TvDetailResponse.toTvDetail(
    countryIsoCode: String
): TvDetail {
    return TvDetail(
        id = id.orZero(),
        genres = genres.orEmpty(),
        createdBy = createdBy?.toListOfCreatedBy(),
        numberOfSeasons = numberOfSeasons.orZero(),
        originalName = originalName.orEmpty(),
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        credit = credits?.toCredit(),
        genresBySeparatedByComma = Utils.getGenresBySeparatedByComma(genreList = genres),
        ratingValue = Utils.calculateRatingBarValue(voteAverage),
        releaseDate = Utils.convertTvSeriesReleaseDateBetweenFirstAndLastDate(
            firstAirDate = firstAirDate,
            lastAirDate = lastAirDate,
            status = status
        ),
        status = status.orEmpty(),
        firstAirDate = firstAirDate.orEmpty(),
        lastAirDate = lastAirDate.orEmpty()
    )
}