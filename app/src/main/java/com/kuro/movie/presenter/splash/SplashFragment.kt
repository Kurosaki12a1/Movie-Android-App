package com.kuro.movie.presenter.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuro.movie.MainNavGraphDirections
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    inflater = FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onInitialize() {
        viewModel.navigateToHome.observe(viewLifecycleOwner) {
            findNavController().navigate(MainNavGraphDirections.actionGlobalAuthFlow())
        }

        viewModel.startTimer()
    }
}