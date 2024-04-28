package com.kuro.movie.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.Resource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Single<Resource<Unit>> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Resource.success(Unit))
                    } else {
                        emitter.onError(task.exception ?: Exception("Unknown error occurred"))
                    }
                }
        }
    }

    override suspend fun createWithEmailAndPassword(
        email: String,
        password: String
    ): Single<Resource<Unit>> {
        return Single.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Resource.success(Unit))
                    } else {
                        emitter.onError(task.exception ?: Exception("Unknown error occurred"))
                    }
                }
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Single<Resource<Unit>> {
        return Single.create { emitter ->
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Resource.success(Unit))
                    } else {
                        emitter.onError(task.exception ?: Exception("Unknown error occurred"))
                    }
                }
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): Single<Resource<Unit>> {
        return Single.create { emitter ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Resource.success(Unit))
                    } else {
                        emitter.onError(task.exception ?: Exception("Unknown error occurred"))
                    }
                }
        }
    }

}