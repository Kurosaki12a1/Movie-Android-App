package com.kuro.movie.domain.usecase.movie

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.firebase.FirebaseMovieRepository
import com.kuro.movie.util.Resource
import javax.inject.Inject

class GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        val userUid = auth.currentUser?.uid
            ?: return Resource.Failure(Exception("You must login to do this operation"))

        val result = firebaseMovieRepository.getMovieInWatchList(
            userUid = userUid
        )

        return when (result) {
            is Resource.Failure -> {
                Resource.failure(result.error, result.errorMessage)
            }


            is Resource.Success -> {
                if (result.value.isNotEmpty()) {
                    result.value.forEach { movie ->
                        localDatabaseRepository.movieLocalRepository.insertMovieToWatchList(movie)
                    }
                }
                Resource.success(Unit)
            }

            Resource.Loading -> {
                Resource.Loading
            }
        }
    }
}