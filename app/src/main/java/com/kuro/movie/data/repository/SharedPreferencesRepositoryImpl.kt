package com.kuro.movie.data.repository

import com.kuro.movie.data.preferences.AppPreferences
import com.kuro.movie.domain.repository.SharedPreferenceRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : SharedPreferenceRepository {
    override fun updateUIMode(uiMode: Int) {
        appPreferences.uiMode = uiMode
    }

    override fun getUIMode(): Int = appPreferences.uiMode
}