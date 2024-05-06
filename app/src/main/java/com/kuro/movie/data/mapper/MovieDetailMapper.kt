package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.response.detail.movie.MovieDetailResponse
import com.kuro.movie.extension.orZero
import com.kuro.movie.util.Utils

fun MovieDetailResponse.toMovieDetail(countryIsoCode: String): MovieDetail {
    return MovieDetail(
        id = id.orZero(),
        genres = genres.orEmpty(),
        imdbId = imdbId,
        originalTitle = originalTitle.orEmpty(),
        title = title.orEmpty(),
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount),
        credit = credits?.toCredit(),
        watchProviders = watchProviders?.results?.toWatchProviderItem(countryIsoCode = countryIsoCode),
        genresBySeparatedByComma = Utils.getGenresBySeparatedByComma(genreList = genres),
        ratingValue = Utils.calculateRatingBarValue(voteAverage = voteAverage.orZero()),
        convertedRuntime = Utils.convertRuntimeAsHourAndMinutes(runtime),
    )
}