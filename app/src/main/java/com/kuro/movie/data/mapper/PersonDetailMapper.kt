package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.PersonDetail
import com.kuro.movie.domain.response.person_detail.PersonDetailResponse
import com.kuro.movie.extension.orZero

fun PersonDetailResponse.toPersonDetail(): PersonDetail {
    return PersonDetail(
        id = id.orZero(),
        name = name.orEmpty(),
        biography = biography.orEmpty(),
        birthday = birthday.orEmpty(),
        combinedCredits = combinedCreditsDto?.toCombinedCredits(),
        deathDay = deathday,
        imdbId = imdbId.orEmpty(),
        knownForDepartment = knownForDepartment.orEmpty(),
        placeOfBirth = placeOfBirth.orEmpty(),
        profilePath = profilePath
    )
}