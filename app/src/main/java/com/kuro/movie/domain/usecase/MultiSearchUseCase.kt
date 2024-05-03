package com.kuro.movie.domain.usecase

import androidx.paging.PagingData
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.repository.ExploreRepository
import io.reactivex.Observable
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(
    private val repository: ExploreRepository,
) {

    operator fun invoke(
        query: String,
        language: String
    ): Observable<PagingData<MultiSearch>> {
        return repository.multiSearch(query = query, language = language)
    }
}