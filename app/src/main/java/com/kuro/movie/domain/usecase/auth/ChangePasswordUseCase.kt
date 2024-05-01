package com.kuro.movie.domain.usecase.auth

import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        oldPassword: String,
        newPassword: String,
        reEnterPassword: String
    ): Single<Resource<Unit>> {
        if (oldPassword == newPassword) {
            return Single.error(Exception("New password cannot be same as old password"))
        }
        if (newPassword != reEnterPassword) {
            return Single.error(Exception("Password does not match"))
        }
        return repository.changePassword(oldPassword, newPassword)
    }
}