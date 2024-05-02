package com.kuro.movie.data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.kuro.movie.domain.repository.firebase.FirebaseCoreRepository
import javax.inject.Inject

class FirebaseCoreRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : FirebaseCoreRepository {
    override fun deleteCollection(
        collectionPath: String,
        batchSize: Int,
        onComplete: () -> Unit
    ) {
        val collection = fireStore.collection(collectionPath)
        collection.limit(batchSize.toLong()).get()
            .addOnCompleteListener { snapShot ->
                if (snapShot.result.isEmpty) {
                    // Force end of recursion
                    onComplete()
                    return@addOnCompleteListener
                }
                // Delete document each batch
                val batch = fireStore.batch()
                snapShot.result.documents.forEach { batch.delete(it.reference) }

                // Commit batch and continue delete if needed
                batch.commit()
                    .addOnSuccessListener {
                        deleteCollection(collectionPath, batchSize, onComplete)
                    }.addOnFailureListener { error ->
                        error.printStackTrace()
                    }
            }.addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}