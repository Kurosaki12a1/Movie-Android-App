package com.kuro.movie.presenter.detail_bottom_sheet

sealed class DetailBottomSheetEvent {
    data object Close : DetailBottomSheetEvent()
    data object Share : DetailBottomSheetEvent()
    data object NavigateToDetailFragment : DetailBottomSheetEvent()

    data object ClickedAddFavoriteList : DetailBottomSheetEvent()
    data object ClickedAddWatchList : DetailBottomSheetEvent()
}