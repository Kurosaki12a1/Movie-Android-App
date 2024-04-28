package com.kuro.movie.data.mapper

import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.GenreList
import com.kuro.movie.domain.response.GenreListResponse
import com.kuro.movie.extension.orZero

fun GenreListResponse.toGenreList(): GenreList {
    val genres = genres?.map {
        Genre(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }.orEmpty()

    return GenreList(genres)
}