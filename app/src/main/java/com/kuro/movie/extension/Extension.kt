package com.kuro.movie.extension

import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.kuro.movie.R

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

val defaultNavOptions = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}

fun NavController.navigateWithAnimation(id: Int) {
    this.navigate(id, null, defaultNavOptions)
}

fun NavController.navigateWithAnimation(directions: NavDirections) {
    this.navigate(directions, defaultNavOptions)
}

fun View.makeVisible() {
    this.isVisible = true
}

fun View.makeGone() {
    this.isVisible = false
}

fun CombinedLoadStates.isLoading(): Boolean {

    return when (this.refresh) {
        is LoadState.Loading -> true
        is LoadState.NotLoading -> false
        else -> false
    }
}

fun CombinedLoadStates.isErrorWithLoadState(): LoadState.Error? {
    return when {
        this.source.refresh is LoadState.Error -> this.source.refresh as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        else -> null
    }
}
