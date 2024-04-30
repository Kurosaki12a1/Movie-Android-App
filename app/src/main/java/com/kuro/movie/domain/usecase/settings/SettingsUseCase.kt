package com.kuro.movie.domain.usecase.settings

import com.kuro.movie.domain.usecase.ui_mode.GetUIModeUseCase
import com.kuro.movie.domain.usecase.ui_mode.UpdateUIModeUseCase

data class SettingsUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
)
