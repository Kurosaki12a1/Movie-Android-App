package com.kuro.movie.domain.usecase.ui_mode

import com.kuro.movie.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class UpdateUIModeUseCase @Inject constructor(
    private val repository: SharedPreferenceRepository
) {
    operator fun invoke(uiMode: Int) {
        repository.updateUIMode(uiMode)
    }
}