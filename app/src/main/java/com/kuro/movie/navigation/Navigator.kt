package com.kuro.movie.navigation

import androidx.navigation.NavController
import com.kuro.movie.MainNavGraphDirections

class Navigator {
    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // navigate on main thread or nav component crashes sometimes (WTF?)
    fun navigateToFlow(navigateFlow: NavigateFlow) = when (navigateFlow) {
        is NavigateFlow.HomeFlow -> navController.navigate(MainNavGraphDirections.actionGlobalHomeFlow())
        is NavigateFlow.AuthFlow -> navController.navigate(MainNavGraphDirections.actionGlobalAuthFlow())
        else -> {
            // TODO
        }
       /* is NavigateFlow.BottomSheetDetailFlow -> navController.navigate(

            MainNavGraphDirections.actionGlobalDetailBottomSheetFlow(
                navigateFlow.movie,
                navigateFlow.tvSeries
            )
        )

        is NavigateFlow.DetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalDetailFlow(
                navigateFlow.movieId,
                navigateFlow.tvSeriesId
            )
        )

        is NavigateFlow.PersonDetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalPersonDetailFlow(
                navigateFlow.personId
            )
        )*/

    }
}