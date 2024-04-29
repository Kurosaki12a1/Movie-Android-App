package com.kuro.movie.domain.repository.data_source.local

interface LocalDatabaseRepository {
    val movieLocalRepository: MovieLocalRepository
    val tvSeriesLocalRepository: TvSeriesLocalRepository

    suspend fun clearDatabase()
}