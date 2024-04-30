package com.kuro.movie.presenter.detail_bottom_sheet

import com.kuro.movie.navigation.NavigateFlow

sealed class DetailBottomUiEvent {
    data class NavigateTo(val navigateFlow: NavigateFlow) : DetailBottomUiEvent()
    data class ShowSnackBar(val text: String) : DetailBottomUiEvent()
    data object PopBackStack : DetailBottomUiEvent()
}
