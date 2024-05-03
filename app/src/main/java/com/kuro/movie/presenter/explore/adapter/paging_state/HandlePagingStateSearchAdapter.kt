package com.kuro.movie.presenter.explore.adapter.paging_state

import com.kuro.movie.core.BasePagingLoadState
import com.kuro.movie.presenter.explore.adapter.SearchRecyclerAdapter

class HandlePagingStateSearchAdapter(
    searchPagingAdapter: SearchRecyclerAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (String) -> Unit
) : BasePagingLoadState() {

    init {
        searchPagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}