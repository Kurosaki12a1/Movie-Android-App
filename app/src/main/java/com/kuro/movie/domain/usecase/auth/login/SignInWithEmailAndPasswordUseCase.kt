package com.kuro.movie.domain.usecase.auth.login

import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.EmailEmptyException
import com.kuro.movie.util.PasswordEmptyException
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Single<Resource<Unit>> {
        return when {
            email.isBlank() -> Single.error(EmailEmptyException())
            password.isBlank() -> Single.error(PasswordEmptyException())
            else -> repo.signInWithEmailAndPassword(email, password)
        }
    }
}