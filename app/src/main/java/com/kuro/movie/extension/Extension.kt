package com.kuro.movie.extension

import android.content.Context
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.kuro.movie.R
import com.kuro.movie.domain.model.Category
import com.kuro.movie.domain.model.Sort
import com.kuro.movie.domain.model.isLatestRelease
import com.kuro.movie.domain.model.isMovie
import com.kuro.movie.domain.model.isPopularity
import com.kuro.movie.util.Constants

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Float.toPx(context : Context) : Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

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

fun List<Int>.toSeparateWithComma(): String {

    if (this.isEmpty()) {
        return ""
    }

    if (this.size == 1) {
        return this[0].toString()
    }

    val stringBuilder = StringBuilder()

    val separator = ","

    for (item in this) {
        stringBuilder.append(item).append(separator)
    }

    stringBuilder.setLength(stringBuilder.length - separator.length)
    return stringBuilder.toString()
}

fun Sort.toDiscoveryQueryString(movieCategory: Category): String {

    val stringBuilder = StringBuilder()

    if (this.isPopularity()) {
        stringBuilder.append(this.value)
        return stringBuilder.append(".desc").toString()
    }

    if (this.isLatestRelease() && movieCategory.isMovie()) {
        stringBuilder.append(this.value)
    } else {
        stringBuilder.append(Constants.DISCOVER_DATE_QUERY_FOR_TV)
    }

    return stringBuilder.append(".desc").toString()
}
