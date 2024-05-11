package com.kuro.movie.domain.repository.person_detail

import com.kuro.movie.domain.response.person_detail.PersonDetailResponse

interface PersonRemoteDataSource {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): PersonDetailResponse
}