package com.kuro.movie.presenter.detail.event

import com.kuro.movie.navigation.NavigateFlow

sealed class DetailUIEvent {
    data class ShowSnackBar(val text: String) : DetailUIEvent()
    data object PopBackStack : DetailUIEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUIEvent()
    data object ShowAlertDialog : DetailUIEvent()
    data class NavigateTo(val navigateFlow: NavigateFlow) : DetailUIEvent()
}