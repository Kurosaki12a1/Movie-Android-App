package com.kuro.movie.domain.repository

import io.reactivex.Single

interface SharedPreferenceRepository {

    fun updateUIMode(uiMode: Int)

    fun getUIMode() : Int
}