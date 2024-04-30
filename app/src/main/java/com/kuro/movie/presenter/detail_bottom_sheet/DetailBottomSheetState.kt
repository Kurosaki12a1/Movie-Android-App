package com.kuro.movie.presenter.detail_bottom_sheet

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

data class DetailBottomSheetState(
    val movie: Movie? = null,
    val tvSeries: TvSeries? = null,
    val doesAddFavorite: Boolean = false,
    val doesAddWatchList: Boolean = false
)