package com.kuro.movie.domain.model

data class Credit(
    val cast: List<Cast>,
    val crew: List<Crew>
)

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?,
    val character: String,
)

data class Crew(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?,
    val department: String
)
