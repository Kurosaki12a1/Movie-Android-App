package com.kuro.movie.extension

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
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
