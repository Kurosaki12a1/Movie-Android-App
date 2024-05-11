package com.kuro.movie.data.repository.peson_detail

import com.kuro.movie.data.data_source.remote.PersonAPI
import com.kuro.movie.domain.repository.person_detail.PersonRemoteDataSource
import com.kuro.movie.domain.response.person_detail.PersonDetailResponse
import com.kuro.movie.util.tryApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonRemoteDataSourceImpl @Inject constructor(
    private val personApi: PersonAPI,
) : PersonRemoteDataSource {
    override suspend fun getPersonDetail(personId: Int, language: String): PersonDetailResponse {
        return withContext(Dispatchers.IO) {
            tryApiCall {
                personApi.getPersonDetail(
                    personId = personId,
                    language = language
                )
            }
        }
    }
}