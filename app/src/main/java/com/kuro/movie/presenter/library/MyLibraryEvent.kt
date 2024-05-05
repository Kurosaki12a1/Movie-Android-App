package com.kuro.movie.presenter.library

sealed class MyLibraryEvent {
    data class SelectedTab(val listTab: LibraryTab) : MyLibraryEvent()
    data class UpdateListType(val chipType: ChipType) : MyLibraryEvent()
}
