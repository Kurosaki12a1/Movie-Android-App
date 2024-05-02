package com.kuro.movie.domain.usecase.tvseries

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.firebase.FirebaseTvSeriesRepository
import com.kuro.movie.util.Resource
import javax.inject.Inject

class GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {

    suspend operator fun invoke(): Resource<Unit> {
        val userUid = auth.currentUser?.uid
            ?: return Resource.Failure(Exception("You must login to do this operation"))

        val result = firebaseTvSeriesRepository.getTvSeriesInWatchList(
            userUid = userUid
        )

        return when (result) {
            is Resource.Failure -> {
                Resource.failure(result.error, result.errorMessage)
            }

            is Resource.Success -> {
                if (result.value.isNotEmpty()) {
                    result.value.forEach { tvSeries ->
                        localDatabaseRepository.tvSeriesLocalRepository.insertTvSeriesToWatchListItem(
                            tvSeries
                        )
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