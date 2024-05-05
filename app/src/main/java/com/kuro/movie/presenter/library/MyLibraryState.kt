package com.kuro.movie.presenter.library

import androidx.annotation.StringRes
import com.kuro.movie.R
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

data class MyLibraryState(
    val selectedTab: LibraryTab = LibraryTab.FAVORITE_LIST,
    val chipType: ChipType = ChipType.MOVIE,
    val isLoading: Boolean = false,
    val movieList: List<Movie> = emptyList(),
    val tvSeriesList: List<TvSeries> = emptyList(),
    val hasAnyMovieInList: Boolean = false,
    @StringRes val errorMessage: Int = R.string.not_tv_in_your_list
)

enum class LibraryTab {
    WATCH_LIST, FAVORITE_LIST
}

enum class ChipType {
    MOVIE,
    TV_SERIES
}

fun LibraryTab.isWatchList(): Boolean = this == LibraryTab.WATCH_LIST
fun LibraryTab.isFavoriteList(): Boolean = this == LibraryTab.FAVORITE_LIST

