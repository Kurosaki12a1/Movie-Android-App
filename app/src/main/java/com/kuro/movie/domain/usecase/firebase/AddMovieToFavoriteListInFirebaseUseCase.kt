package com.kuro.movie.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.remote.FirebaseCoreMovieRepository
import com.kuro.movie.util.Resource
import javax.inject.Inject

class AddMovieToFavoriteListInFirebaseUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    suspend operator fun invoke(
        moviesInFavoriteList: List<Movie>
    ): Resource<Unit> {
        val userUid = auth.currentUser?.uid ?: return Resource.failure(Exception("User not Found"))

        return firebaseCoreMovieRepository.addMovieToFavoriteList(
            userUid = userUid,
            movieInFavoriteList = moviesInFavoriteList
        )
    }
}