package com.kuro.movie.presenter.settings.change_profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class ChangeProfileUIState(
    val isLoading: Boolean = false,
    val currentProfile: FirebaseUser? = null,
    val newDisplayName: String = "",
    val photoUri: Uri? = null
)