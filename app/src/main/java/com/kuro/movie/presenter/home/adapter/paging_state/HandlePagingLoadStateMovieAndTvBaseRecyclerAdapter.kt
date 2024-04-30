package com.kuro.movie.presenter.home.adapter.paging_state

import com.kuro.movie.core.BaseMovieAndTvRecyclerAdapter
import com.kuro.movie.core.BasePagingLoadState

class HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter<T : Any>(
    pagingAdapter: BaseMovieAndTvRecyclerAdapter<T>,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (String) -> Unit = {}
) : BasePagingLoadState() {

    init {
        pagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}