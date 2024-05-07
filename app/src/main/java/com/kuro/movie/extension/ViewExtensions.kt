package com.kuro.movie.extension

import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout
import com.kuro.movie.R

fun View.makeVisible() {
    this.isVisible = true
}

fun View.makeGone() {
    this.isVisible = false
}

fun ShimmerFrameLayout.onLoading(){
    this.isVisible = true
    this.startShimmer()
}

fun ShimmerFrameLayout.onNotLoading(){
    this.isVisible = false
    this.stopShimmer()
}


fun ImageButton.setAddFavoriteIconByFavoriteState(isFavorite: Boolean) {
    if (isFavorite) {
        setImageResource(R.drawable.ic_favorite_white)
    } else {
        setImageResource(R.drawable.ic_favorite_border_white)
    }
}

fun ImageButton.setWatchListIconByWatchState(isAddedWatchList: Boolean) {
    if (isAddedWatchList) {
        setImageResource(R.drawable.ic_video_white)
    } else {
        setImageResource(R.drawable.outline_video_library_24)
    }
}