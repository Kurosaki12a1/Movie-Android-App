package com.kuro.movie.navigation

import androidx.navigation.NavController
import com.kuro.movie.MainNavGraphDirections
import com.kuro.movie.R
import com.kuro.movie.extension.navigateWithAnimation
import com.kuro.movie.presenter.auth.login.LoginFragmentDirections
import com.kuro.movie.presenter.settings.SettingsFragmentDirections
import com.kuro.movie.presenter.settings.profile.ProfileFragmentDirections
import java.lang.ref.WeakReference

class Navigator {
    private lateinit var navController: WeakReference<NavController>

    fun setNavController(navController: NavController) {
        this.navController = WeakReference(navController)
    }

    // Navigate on main thread or nav component crashes sometimes (WTF?)
    fun navigateToFlow(navigateFlow: NavigateFlow) = when (navigateFlow) {
        is NavigateFlow.HomeFlow -> navController.get()
            ?.navigateWithAnimation(MainNavGraphDirections.actionGlobalHomeFlow())

        is NavigateFlow.AuthFlow -> navController.get()
            ?.navigateWithAnimation(MainNavGraphDirections.actionGlobalAuthFlow())

        is NavigateFlow.AuthFlowFromProfile -> {
            // Attempt to pop the back stack to the 'auth_flow' destination.
            // The second argument 'false' means it will not pop the 'auth_flow' itself if found.
            if (navController.get()?.popBackStack(R.id.auth_flow, false) == false) {
                // If 'auth_flow' is not found in the back stack or could not be popped to,
                // then clear the back stack up to and including the 'main_nav_graph'.
                navController.get()?.popBackStack(R.id.main_nav_graph, true)
                // Navigate to 'auth_flow' destination with a custom animation.
                navController.get()?.navigateWithAnimation(R.id.auth_flow)
            }
            // Return Kotlin's Unit, indicating this block of code doesn't return any meaningful value.
            Unit
        }

        is NavigateFlow.SignUpFlow -> navController.get()
            ?.navigateWithAnimation(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())

        is NavigateFlow.ForgetPasswordFlow -> navController.get()?.navigateWithAnimation(
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(navigateFlow.email)
        )

        // This does not need animation
        is NavigateFlow.BottomSheetDetailFlow -> navController.get()?.navigate(
            MainNavGraphDirections.actionGlobalDetailBottomSheetFlow(
                navigateFlow.movie,
                navigateFlow.tvSeries
            )
        )

        is NavigateFlow.ProfileFlow -> navController.get()?.navigateWithAnimation(
            SettingsFragmentDirections.actionSettingsFragmentToProfileFragment()
        )

        is NavigateFlow.ChangeProfileFlow -> navController.get()?.navigateWithAnimation(
            ProfileFragmentDirections.actionProfileFragmentToChangeProfile()
        )

        is NavigateFlow.ChangePasswordFlow -> navController.get()?.navigateWithAnimation(
            ProfileFragmentDirections.actionProfileFragmentToChangePassword()
        )

        is NavigateFlow.DetailFlow -> navController.get()?.navigateWithAnimation(
            MainNavGraphDirections.actionGlobalDetailFlow(
                navigateFlow.movieId,
                navigateFlow.tvSeriesId
            )
        )

        is NavigateFlow.PersonDetailFlow -> navController.get()?.navigateWithAnimation(
            MainNavGraphDirections.actionGlobalPersonDetailFlow(
                navigateFlow.personId
            )
        )
    }
}