package com.kuro.movie.navigation

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

sealed interface NavigateFlow {
    // Auth Flow
    data object AuthFlow : NavigateFlow
    data object SignUpFlow : NavigateFlow
    data class ForgetPasswordFlow(val email : String) : NavigateFlow

    // Bottom Navigation Flow
    data object HomeFlow : NavigateFlow

    data object SettingsFlow : NavigateFlow

    data class BottomSheetDetailFlow(val movie: Movie?, val tvSeries: TvSeries?) : NavigateFlow

    data class DetailFlow(val movieId: Int = 0, val tvSeriesId: Int = 0) :
        NavigateFlow

    data class PersonDetailFlow(val personId: Int) : NavigateFlow
}