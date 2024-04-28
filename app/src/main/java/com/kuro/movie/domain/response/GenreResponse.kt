package com.kuro.movie.domain.response

data class GenreResponse(
    val id: Int?,
    val name: String?
)

data class GenreListResponse(
    val genres: List<GenreResponse>?
)