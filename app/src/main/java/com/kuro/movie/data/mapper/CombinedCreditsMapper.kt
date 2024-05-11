package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.CombinedCredits
import com.kuro.movie.domain.response.person_detail.CombinedCreditsResponse

fun CombinedCreditsResponse.toCombinedCredits(): CombinedCredits {
    return CombinedCredits(
        cast = cast?.map { it.toCastForPerson() }.orEmpty(),
        crew = crew?.map { it.toCrewForPerson() }.orEmpty()
    )
}