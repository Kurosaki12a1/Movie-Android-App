package com.kuro.movie.util

import android.content.Context
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

inline fun <T> safeCallWithForFirebase(
    context: Context,
    setFirebaseAuthErrorMessage: (exception: FirebaseAuthException) -> Resource<T> = {
        Resource.failure(it, it.localizedMessage)
    },
    call: () -> Resource<T>,
): Resource<T> {
    return try {
        call()
    } catch (e: FirebaseFirestoreException) {
        e.printStackTrace()
        Resource.failure(e, e.localizedMessage)
        FirebaseFireStoreErrorMessage.setExceptionToFirebaseMessage(context, e)
    } catch (exception: FirebaseAuthException) {
        setFirebaseAuthErrorMessage(exception)
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.failure(e, e.localizedMessage)
    }
}