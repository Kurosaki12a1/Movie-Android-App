package com.kuro.movie.data.repository.peson_detail

import com.kuro.movie.data.mapper.toPersonDetail
import com.kuro.movie.domain.model.PersonDetail
import com.kuro.movie.domain.repository.PersonRepository
import com.kuro.movie.domain.repository.person_detail.PersonRemoteDataSource
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeApiCallReturnResource
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personRemoteDataSource: PersonRemoteDataSource
) : PersonRepository {

    override suspend fun getPersonDetail(personId: Int, language: String): Resource<PersonDetail> {
        return safeApiCallReturnResource {
            personRemoteDataSource.getPersonDetail(
                personId = personId,
                language = language
            ).toPersonDetail()
        }
    }
}