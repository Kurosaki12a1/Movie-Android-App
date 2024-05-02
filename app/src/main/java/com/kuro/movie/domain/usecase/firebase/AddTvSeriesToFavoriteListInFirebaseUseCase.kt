package com.kuro.movie.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.kuro.movie.util.Resource
import javax.inject.Inject

class AddTvSeriesToFavoriteListInFirebaseUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
) {

    suspend operator fun invoke(
        tvSeriesInFavoriteList: List<TvSeries>,
    ): Resource<Unit> {
        val userUid = auth.currentUser?.uid ?: return Resource.failure(Exception("User not Found"))

        return firebaseCoreTvSeriesRepository.addTvSeriesToFavoriteList(
            userUid = userUid,
            tvSeriesInFavoriteList = tvSeriesInFavoriteList
        )
    }
}