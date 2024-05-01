package com.kuro.movie.presenter.settings.profile

import com.google.firebase.auth.FirebaseUser

data class ProfileUIState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val userProfile: FirebaseUser? = null,
    val isFirebaseAccount : Boolean = false
)