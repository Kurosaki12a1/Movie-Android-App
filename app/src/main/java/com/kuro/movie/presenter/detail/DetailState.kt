package com.kuro.movie.presenter.detail

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.TvDetail

data class DetailState(
    val isLoading: Boolean = false,
    val videosLoading: Boolean = false,
    val movieRecommendation: List<Movie>? = null,
    val tvRecommendation: List<TvSeries>? = null,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val doesAddFavorite: Boolean = false,
    val doesAddWatchList: Boolean = false,
    val selectedTab: SelectableTab = SelectableTab.RECOMMENDATION_TAB,
    val movieId: Int? = null,
    val tvId: Int? = null,
)

enum class SelectableTab {
    RECOMMENDATION_TAB,
    TRAILER_TAB
}

fun DetailState.isSelectedRecommendationTab(): Boolean {
    return this.selectedTab == SelectableTab.RECOMMENDATION_TAB
}

fun DetailState.isSelectedTrailerTab(): Boolean {
    return this.selectedTab == SelectableTab.TRAILER_TAB
}

fun DetailState.isNotNullTvDetail(): Boolean {
    return tvId != null
}