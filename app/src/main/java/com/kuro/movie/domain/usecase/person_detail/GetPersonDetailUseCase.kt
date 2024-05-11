package com.kuro.movie.domain.usecase.person_detail

import com.kuro.movie.domain.model.PersonDetail
import com.kuro.movie.domain.repository.PersonRepository
import com.kuro.movie.util.DateFormatUtils
import com.kuro.movie.util.Resource
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(
        personId: Int,
        language: String
    ): Resource<PersonDetail> {
        val resource = repository.getPersonDetail(personId = personId, language = language)
        return when (resource) {
            is Resource.Success -> {
                resource.value.let { personDetail ->
                    val result = personDetail.copy(
                        birthday = DateFormatUtils.convertDateFormat(inputDate = personDetail.birthday),
                        deathDay = if (personDetail.deathDay != null) DateFormatUtils.convertDateFormat(
                            inputDate = personDetail.deathDay
                        ) else null,
                        combinedCredits = personDetail.combinedCredits?.copy(
                            cast = personDetail.combinedCredits.cast.sortedByDescending { it.popularity },
                            crew = personDetail.combinedCredits.crew.filter { it.department == "Directing" }
                                .sortedByDescending { it.popularity }
                        )
                    )
                    Resource.Success(result)
                }
            }

            else -> resource
        }
    }
}