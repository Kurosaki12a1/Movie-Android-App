package com.kuro.movie.domain.usecase.auth

import com.kuro.movie.domain.repository.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(oldPassword: String, newPassword: String) =
        repository.changePassword(oldPassword, newPassword)
}