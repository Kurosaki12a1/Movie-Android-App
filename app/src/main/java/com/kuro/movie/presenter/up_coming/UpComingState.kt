package com.kuro.movie.presenter.up_coming

data class UpComingState(
    val isLoading: Boolean = false,
    val error: String = "",
)