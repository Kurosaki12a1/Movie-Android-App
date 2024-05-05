package com.kuro.movie.presenter.up_coming.adapter.paging_state

import com.kuro.movie.core.BasePagingLoadState
import com.kuro.movie.presenter.up_coming.adapter.UpComingMovieAdapter

class HandlePagingStateUpComingPagingAdapter(
    upComingPagingAdapter: UpComingMovieAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (String) -> Unit
) : BasePagingLoadState() {
    init {
        upComingPagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}