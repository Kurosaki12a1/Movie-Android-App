package com.kuro.movie.presenter.settings

import androidx.appcompat.app.AppCompatDelegate

data class SettingsUiState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val uiMode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
)