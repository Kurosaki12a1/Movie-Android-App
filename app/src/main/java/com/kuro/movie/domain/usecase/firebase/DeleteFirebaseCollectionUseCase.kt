package com.kuro.movie.domain.usecase.firebase

import com.kuro.movie.domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class DeleteFirebaseCollectionUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(collectionPath: String, batchSize: Int = 10, onComplete: () -> Unit) {
        return repository.deleteCollection(collectionPath, batchSize, onComplete)
    }
}