package com.kuro.movie.domain.response.detail

data class CreditResponse(
    val cast: List<CastResponse>?,
    val crew: List<CrewResponse>?
)