package com.kuro.movie.domain.repository.firebase

interface FirebaseCoreRepository {
    fun deleteCollection(
        collectionPath: String,
        batchSize: Int = 10,
        onComplete : () -> Unit
    )
}