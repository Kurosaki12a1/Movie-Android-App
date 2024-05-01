package com.kuro.movie.presenter.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuro.movie.MainNavGraphDirections
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSplashBinding
import com.kuro.movie.extension.navigateWithAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    inflater = FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onInitialize() {
        viewModel.navigateRoute.observe(viewLifecycleOwner) {
            if (it == SplashRoute.AUTH) {
                findNavController().navigateWithAnimation(MainNavGraphDirections.actionGlobalAuthFlow())
            } else if (it == SplashRoute.HOME) {
                findNavController().navigateWithAnimation(MainNavGraphDirections.actionGlobalHomeFlow())
            }
        }
        viewModel.startTimer()
    }
}