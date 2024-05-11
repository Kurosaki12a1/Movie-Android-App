package com.kuro.movie.domain.response.person_detail

data class CombinedCreditsResponse(
    val cast: List<CastForPersonResponse>?,
    val crew: List<CrewForPersonResponse>?
)