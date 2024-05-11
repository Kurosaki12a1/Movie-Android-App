package com.kuro.movie.domain.model

data class CombinedCredits(
    val cast: List<CastForPerson>,
    val crew: List<CrewForPerson>
)