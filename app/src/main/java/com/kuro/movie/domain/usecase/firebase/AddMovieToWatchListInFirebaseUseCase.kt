package com.kuro.movie.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.firebase.FirebaseCoreMovieRepository
import com.kuro.movie.util.Resource
import javax.inject.Inject

class AddMovieToWatchListInFirebaseUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    suspend operator fun invoke(
        moviesInWatchList: List<Movie>
    ): Resource<Unit> {
        val userUid = auth.currentUser?.uid ?: return Resource.failure(Exception("User not Found"))

        return firebaseCoreMovieRepository.addMovieToWatchList(
            userUid = userUid,
            moviesInWatchList = moviesInWatchList
        )
    }
}