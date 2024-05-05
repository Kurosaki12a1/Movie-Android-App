package com.kuro.movie.presenter.up_coming

import com.kuro.movie.domain.model.UpComingMovie

sealed class UpComingEvent {

    data object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    data object NotLoading : UpComingEvent()

    data class OnClickRemindMe(
        val upcomingMovie: UpComingMovie
    ) : UpComingEvent()
}
