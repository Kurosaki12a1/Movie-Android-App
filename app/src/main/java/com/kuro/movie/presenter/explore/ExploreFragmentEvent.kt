package com.kuro.movie.presenter.explore

sealed class ExploreFragmentEvent {
    data class MultiSearch(val query: String) : ExploreFragmentEvent()
    data object RemoveQuery : ExploreFragmentEvent()
}
