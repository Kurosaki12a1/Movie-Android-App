package com.kuro.movie.presenter.detail.event

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.presenter.detail.SelectableTab

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()
    data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    data class ClickActorName(val actorId: Int) : DetailEvent()
    data class SelectedTab(val selectedTab: SelectableTab) : DetailEvent()
    data object ClickedAddWatchList : DetailEvent()
    data object ClickedAddFavoriteList : DetailEvent()
    data class ClickRecommendationItemClick(
        val tvSeries: TvSeries? = null,
        val movie: Movie? = null
    ) : DetailEvent()
}