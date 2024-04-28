package com.kuro.movie.domain.repository

import com.google.firebase.auth.AuthCredential
import com.kuro.movie.util.Resource
import io.reactivex.Single

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Single<Resource<Unit>>

    suspend fun createWithEmailAndPassword(
        email: String,
        password: String
    ): Single<Resource<Unit>>

    suspend fun sendPasswordResetEmail(
        email: String
    ): Single<Resource<Unit>>

    suspend fun signInWithCredential(
        credential: AuthCredential
    ): Single<Resource<Unit>>
}