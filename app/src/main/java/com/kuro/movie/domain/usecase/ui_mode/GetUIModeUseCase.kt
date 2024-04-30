package com.kuro.movie.domain.usecase.ui_mode

import com.kuro.movie.domain.repository.SharedPreferenceRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUIModeUseCase @Inject constructor(
    private val repository: SharedPreferenceRepository
) {
    operator fun invoke(): Int = repository.getUIMode()
}