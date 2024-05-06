package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.CreatedBy
import com.kuro.movie.domain.response.detail.tv.CreatedByResponse
import com.kuro.movie.extension.orZero

fun List<CreatedByResponse>.toListOfCreatedBy(): List<CreatedBy> {
    return map {
        CreatedBy(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }
}