package com.kuro.movie.presenter.home

import androidx.fragment.app.viewModels
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    inflater = FragmentHomeBinding::inflate
) {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onInitialize() {

    }
}