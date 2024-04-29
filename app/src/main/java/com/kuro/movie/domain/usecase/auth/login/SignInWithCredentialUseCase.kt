package com.kuro.movie.domain.usecase.auth.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(
        task: Task<GoogleSignInAccount>
    ): Single<Resource<Unit>> {
        val account: GoogleSignInAccount = task.result ?: return Single.error(task.exception)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return repo.signInWithCredential(credential)
    }
}