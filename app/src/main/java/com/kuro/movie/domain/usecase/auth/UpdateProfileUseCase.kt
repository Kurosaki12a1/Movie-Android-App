package com.kuro.movie.domain.usecase.auth

import android.net.Uri
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.util.Resource
import io.reactivex.Single
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(displayName: String?, photoUri: Uri?): Single<Resource<Unit>> {
        if (displayName.isNullOrEmpty()) return Single.error(Exception("Display name cannot be empty"))
        return repository.updateProfile(displayName, photoUri)
    }
}