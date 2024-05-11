package com.kuro.movie.navigation

import androidx.navigation.NavController
import com.kuro.movie.MainNavGraphDirections
import com.kuro.movie.R
import com.kuro.movie.extension.navigateWithAnimation
import com.kuro.movie.presenter.auth.login.LoginFragmentDirections
import com.kuro.movie.presenter.home.HomeFragmentDirections
import com.kuro.movie.presenter.settings.SettingsFragmentDirections
import com.kuro.movie.presenter.settings.profile.ProfileFragmentDirections

class Navigator {
    private var navController: NavController? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun cleanUp() {
        navController = null
    }

    // Navigate on main thread or nav component crashes sometimes (WTF?)
    fun navigateToFlow(navigateFlow: NavigateFlow) = when (navigateFlow) {
        is NavigateFlow.HomeFlow -> navController?.navigateWithAnimation(MainNavGraphDirections.actionGlobalHomeFlow())

        is NavigateFlow.AuthFlow -> navController?.navigateWithAnimation(MainNavGraphDirections.actionGlobalAuthFlow())

        is NavigateFlow.SeeAllFlow -> navController?.navigateWithAnimation(
            HomeFragmentDirections.actionHomeFragmentToSeeAllFragment(
                navigateFlow.categoryPosition,
                navigateFlow.title
            )
        )

        is NavigateFlow.AuthFlowFromProfile -> {
            // Attempt to pop the back stack to the 'auth_flow' destination.
            // The second argument 'false' means it will not pop the 'auth_flow' itself if found.
            if (navController?.popBackStack(R.id.auth_flow, false) == false) {
                // If 'auth_flow' is not found in the back stack or could not be popped to,
                // then clear the back stack up to and including the 'main_nav_graph'.
                navController?.popBackStack(R.id.main_nav_graph, true)
                // Navigate to 'auth_flow' destination with a custom animation.
                navController?.navigateWithAnimation(R.id.auth_flow)
            }
            // Return Kotlin's Unit, indicating this block of code doesn't return any meaningful value.
            Unit
        }

        is NavigateFlow.SignUpFlow -> navController?.navigateWithAnimation(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())

        is NavigateFlow.ForgetPasswordFlow -> navController?.navigateWithAnimation(
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(navigateFlow.email)
        )

        // This does not need animation
        is NavigateFlow.BottomSheetDetailFlow -> navController?.navigate(
            MainNavGraphDirections.actionGlobalDetailBottomSheetFlow(
                navigateFlow.movie,
                navigateFlow.tvSeries
            )
        )

        is NavigateFlow.ProfileFlow -> navController?.navigateWithAnimation(
            SettingsFragmentDirections.actionSettingsFragmentToProfileFragment()
        )

        is NavigateFlow.ChangeProfileFlow -> navController?.navigateWithAnimation(
            ProfileFragmentDirections.actionProfileFragmentToChangeProfile()
        )

        is NavigateFlow.ChangePasswordFlow -> navController?.navigateWithAnimation(
            ProfileFragmentDirections.actionProfileFragmentToChangePassword()
        )

        is NavigateFlow.DetailFlow -> navController?.navigateWithAnimation(
            MainNavGraphDirections.actionGlobalDetailFlow(
                navigateFlow.movieId,
                navigateFlow.tvSeriesId
            )
        )

        is NavigateFlow.PersonDetailFlow -> navController?.navigateWithAnimation(
            MainNavGraphDirections.actionGlobalPersonDetailFlow(
                navigateFlow.personId
            )
        )
    }
}