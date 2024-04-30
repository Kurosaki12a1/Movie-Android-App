package com.kuro.movie.domain.usecase.firebase

import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.util.Resource
import javax.inject.Inject

class GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(): Resource<Unit> {
        val tvSeriesInWatchList =
            localDatabaseUseCases.getTvSeriesInWatchListUseCase()

        return firebaseCoreUseCases.addTvSeriesToWatchListInFirebaseUseCase(
            tvSeriesInWatchList = tvSeriesInWatchList
        )
    }
}