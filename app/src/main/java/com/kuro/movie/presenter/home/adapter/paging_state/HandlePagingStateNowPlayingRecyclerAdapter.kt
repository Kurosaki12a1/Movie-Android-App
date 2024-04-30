package com.kuro.movie.presenter.home.adapter.paging_state

import com.kuro.movie.core.BasePagingLoadState
import com.kuro.movie.presenter.home.adapter.NowPlayingRecyclerAdapter

class HandlePagingStateNowPlayingRecyclerAdapter(
    nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (String) -> Unit = {}
) : BasePagingLoadState() {

    init {
        nowPlayingRecyclerAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}