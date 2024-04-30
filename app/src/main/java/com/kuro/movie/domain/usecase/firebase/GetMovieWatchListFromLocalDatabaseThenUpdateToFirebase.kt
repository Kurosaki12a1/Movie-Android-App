package com.kuro.movie.domain.usecase.firebase

import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.util.Resource
import javax.inject.Inject

class GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(): Resource<Unit> {
        val moviesInWatchList = localDatabaseUseCases.getMoviesInWatchListUseCase()
        return firebaseCoreUseCases.addMovieToWatchListInFirebaseUseCase(
            moviesInWatchList = moviesInWatchList
        )
    }
}