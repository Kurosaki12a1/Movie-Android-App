package com.kuro.movie.domain.usecase.database

import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import javax.inject.Inject

class ClearAllDatabaseUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}