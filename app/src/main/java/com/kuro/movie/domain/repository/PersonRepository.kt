package com.kuro.movie.domain.repository

import com.kuro.movie.domain.model.PersonDetail
import com.kuro.movie.util.Resource

interface PersonRepository {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): Resource<PersonDetail>
}