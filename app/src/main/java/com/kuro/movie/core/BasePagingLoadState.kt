package com.kuro.movie.core

import androidx.paging.CombinedLoadStates
import com.kuro.movie.extension.isErrorWithLoadState
import com.kuro.movie.extension.isLoading
import java.io.IOException

open class BasePagingLoadState {

    protected fun handlePagingLoadState(
        loadStates: CombinedLoadStates,
        onLoading: () -> Unit,
        onNotLoading: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (loadStates.isLoading()) {
            onLoading()
        } else {
            onNotLoading()
        }
        loadStates.isErrorWithLoadState()?.let { loadStateError ->
            if (loadStateError.error is IOException) {
                onError("Oops! Please check your internet connection.")
            } else {
                onError("Oops! Something went wrong. Please try again.")
            }
        }
    }
}

