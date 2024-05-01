package com.kuro.movie.presenter.settings

import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseUser

data class SettingsUiState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val userProfile: FirebaseUser? = null,
    val uiMode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
)