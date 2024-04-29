package com.kuro.movie.data.repository.data_source.local

import com.kuro.movie.data.data_source.local.AppDatabase
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    override val movieLocalRepository: MovieLocalRepository,
    override val tvSeriesLocalRepository: TvSeriesLocalRepository
) : LocalDatabaseRepository {

    override suspend fun clearDatabase() {
        database.clearAllTables()
    }
}