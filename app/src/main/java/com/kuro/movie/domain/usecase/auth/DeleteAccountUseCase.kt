package com.kuro.movie.domain.usecase.auth

import com.kuro.movie.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.deleteAccount()
}