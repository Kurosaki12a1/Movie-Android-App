package com.kuro.movie.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.Resource
import io.reactivex.Single
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

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Single<Resource<Unit>> {
        val user = auth.currentUser
        val email = user?.email ?: return Single.error(Exception("No user logged in"))

        // Create AuthCredential with old password
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        return Single.create { emitter ->
            user.reauthenticate(credential).addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    // If re-authentication is successful, update password
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            emitter.onSuccess(Resource.success(Unit))
                        } else {
                            emitter.onError(
                                updateTask.exception ?: Exception("Failed to update password")
                            )
                        }
                    }
                } else {
                    emitter.onError(authTask.exception ?: Exception("Authentication failed"))
                }
            }
        }
    }

    override suspend fun deleteAccount(): Single<Resource<Unit>> {
        return Single.create { emitter ->
            auth.currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    emitter.onSuccess(Resource.success(Unit))
                } else {
                    emitter.onError(it.exception ?: Exception("Unknown error occurred"))
                }
            } ?: emitter.onError(Exception("User is not authenticated"))
        }
    }

}