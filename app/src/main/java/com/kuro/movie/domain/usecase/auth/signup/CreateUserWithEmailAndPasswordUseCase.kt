package com.kuro.movie.domain.usecase.auth.signup

import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.EmailEmptyException
import com.kuro.movie.util.PasswordEmptyException
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Single<Resource<Unit>> {
        if (email.isBlank()) {
            return Single.error(EmailEmptyException())
        }

        if (password.isBlank()) {
            return Single.error(PasswordEmptyException())
        }

        return repo.createWithEmailAndPassword(email, password)
    }
}