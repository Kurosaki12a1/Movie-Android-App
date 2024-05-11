package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.CastForPerson
import com.kuro.movie.domain.response.person_detail.CastForPersonResponse
import com.kuro.movie.extension.orZero

fun CastForPersonResponse.toCastForPerson(): CastForPerson {
    return CastForPerson(
        id = id.orZero(),
        name = name,
        originalName = originalName,
        originalTitle = originalTitle,
        character = character.orEmpty(),
        firstAirDate = firstAirDate,
        mediaType = mediaType.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        popularity = popularity.orZero(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero()
    )
}