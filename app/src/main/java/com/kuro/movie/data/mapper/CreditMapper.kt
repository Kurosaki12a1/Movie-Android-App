package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.Cast
import com.kuro.movie.domain.model.Credit
import com.kuro.movie.domain.model.Crew
import com.kuro.movie.domain.response.detail.CastResponse
import com.kuro.movie.domain.response.detail.CreditResponse
import com.kuro.movie.domain.response.detail.CrewResponse
import com.kuro.movie.extension.orZero

fun CreditResponse.toCredit(): Credit {
    return Credit(
        cast = cast?.toCast().orEmpty(),
        crew = crew?.toCrew().orEmpty()
    )
}

fun List<CastResponse>.toCast(): List<Cast> {
    return map {
        Cast(
            id = it.id.orZero(),
            originalName = it.originalName.orEmpty(),
            name = it.name.orEmpty(),
            profilePath = it.profilePath,
            character = it.character.orEmpty()
        )
    }
}

fun List<CrewResponse>.toCrew(): List<Crew> {
    return map {
        Crew(
            id = it.id.orZero(),
            name = it.name.orEmpty(),
            originalName = it.originalName.orEmpty(),
            profilePath = it.profilePath,
            department = it.department.orEmpty()
        )
    }
}