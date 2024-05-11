package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.CrewForPerson
import com.kuro.movie.domain.response.person_detail.CrewForPersonResponse
import com.kuro.movie.extension.orZero

fun CrewForPersonResponse.toCrewForPerson(): CrewForPerson {
    return CrewForPerson(
        id = id.orZero(),
        department = department.orEmpty(),
        firstAirDate = firstAirDate,
        job = job.orEmpty(),
        mediaType = mediaType.orEmpty(),
        name = name,
        originalName = originalName,
        originalTitle = originalTitle,
        overview = overview.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        popularity = popularity.orZero(),
        title = title
    )
}