package com.kuro.movie.navigation

import androidx.navigation.NavController
import com.kuro.movie.MainNavGraphDirections
import com.kuro.movie.extension.navigateWithAnimation
import com.kuro.movie.presenter.auth.login.LoginFragmentDirections

class Navigator {
    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    // Navigate on main thread or nav component crashes sometimes (WTF?)
    fun navigateToFlow(navigateFlow: NavigateFlow) = when (navigateFlow) {
        is NavigateFlow.HomeFlow -> navController.navigateWithAnimation(MainNavGraphDirections.actionGlobalHomeFlow())
        is NavigateFlow.AuthFlow -> navController.navigateWithAnimation(MainNavGraphDirections.actionGlobalAuthFlow())
        is NavigateFlow.SignUpFlow -> navController.navigateWithAnimation(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        is NavigateFlow.ForgetPasswordFlow -> navController.navigateWithAnimation(
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(navigateFlow.email)
        )
        // This does not need animation
        is NavigateFlow.BottomSheetDetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalDetailBottomSheetFlow(
                navigateFlow.movie,
                navigateFlow.tvSeries
            )
        )

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