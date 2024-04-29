package com.kuro.movie.domain.usecase.auth.forgetpassword

import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.EmailEmptyException
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(
    private val repo : AuthRepository
) {
    suspend operator fun invoke(
        email: String
    ): Single<Resource<Unit>> {
        if (email.isBlank()) {
            return Single.error(EmailEmptyException())
        }
        return repo.sendPasswordResetEmail(email)
    }
}