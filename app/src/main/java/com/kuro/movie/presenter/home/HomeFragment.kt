package com.kuro.movie.presenter.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.FragmentHomeBinding
import com.kuro.movie.extension.makeGone
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.home.adapter.HomeAdapter
import com.kuro.movie.presenter.home.adapter.HomeAdapterListener
import com.kuro.movie.util.Constants.NOW_PLAYING
import com.kuro.movie.util.Constants.POPULAR_MOVIE
import com.kuro.movie.util.Constants.POPULAR_TV_SERIES
import com.kuro.movie.util.Constants.TOP_RATED_MOVIE
import com.kuro.movie.util.Constants.TOP_RATED_TV_SERIES
import com.kuro.movie.util.observerLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    inflater = FragmentHomeBinding::inflate
) {
    private val homeViewModel: HomeViewModel by viewModels()

    private var homeAdapter: HomeAdapter? = null

    override fun onInitialize() {
        addOnBackPressedCallBack { }
        setUpAdapters()
        setUpView()
        setUpObservers()
    }

    private fun setUpAdapters() {
        homeAdapter = HomeAdapter(viewLifecycleOwner.lifecycleScope)
        homeAdapter?.setListener(object : HomeAdapterListener {
            override fun onError() {
                hideScrollViewAndShowErrorScreen()
            }

            override fun onItemClick(movie: Movie?, tvSeries: TvSeries?, categoryPosition: Int) {
                when (categoryPosition) {
                    NOW_PLAYING -> {
                        navigateToFlow(
                            NavigateFlow.BottomSheetDetailFlow(movie = movie, tvSeries = null)
                        )
                    }

                    POPULAR_MOVIE -> {
                        navigateToFlow(
                            NavigateFlow.BottomSheetDetailFlow(movie = movie, tvSeries = null)
                        )
                    }

                    POPULAR_TV_SERIES -> {
                        navigateToFlow(
                            NavigateFlow.BottomSheetDetailFlow(movie = null, tvSeries = tvSeries)
                        )
                    }

                    TOP_RATED_MOVIE -> {
                        navigateToFlow(
                            NavigateFlow.BottomSheetDetailFlow(movie = movie, tvSeries = null)
                        )
                    }

                    TOP_RATED_TV_SERIES -> {
                        navigateToFlow(
                            NavigateFlow.BottomSheetDetailFlow(movie = null, tvSeries = tvSeries)
                        )
                    }
                }
            }

            override fun onSeeAllClick(title: String, categoryPosition: Int) {
                navigateToFlow(
                    NavigateFlow.SeeAllFlow(
                        title = title,
                        categoryPosition = categoryPosition
                    )
                )
            }
        })
        binding.homeRecyclerView.adapter = homeAdapter
    }

    private fun setUpView() {
        binding.errorScreen.btnError.setOnClickListener {
            hideErrorScreenAndShowScrollView()
            homeViewModel.fetchData()
        }
    }

    private fun setUpObservers() {
        observerLiveData(homeViewModel.nowPlayingMovie) {
            homeAdapter?.setData(
                position = NOW_PLAYING,
                data = HomeModel.NowPlayingItem(it, getString(R.string.now_playing))
            )
        }
        observerLiveData(homeViewModel.popularMovie) {
            homeAdapter?.setData(
                position = POPULAR_MOVIE,
                data = HomeModel.PopularMoviesItem(it, getString(R.string.popular_movies))
            )
        }
        observerLiveData(homeViewModel.topRatedMovie) {
            homeAdapter?.setData(
                position = TOP_RATED_MOVIE,
                data = HomeModel.TopRatedMoviesItem(it, getString(R.string.top_rated_movies))
            )
        }
        observerLiveData(homeViewModel.popularTvSeries) {
            homeAdapter?.setData(
                position = POPULAR_TV_SERIES,
                data = HomeModel.PopularTvSeriesItem(it, getString(R.string.popular_tv_series))
            )
        }
        observerLiveData(homeViewModel.topRatedTvSeries) {
            homeAdapter?.setData(
                position = TOP_RATED_TV_SERIES,
                data = HomeModel.TopRatedTvSeriesItem(
                    it,
                    getString(R.string.top_rated_tv_series)
                )
            )
        }
    }

    private fun hideScrollViewAndShowErrorScreen() {
        binding.homeRecyclerView.makeGone()
        binding.errorScreen.errorScreen.makeVisible()
    }

    private fun hideErrorScreenAndShowScrollView() {
        binding.errorScreen.errorScreen.makeGone()
        binding.homeRecyclerView.makeVisible()
    }

    override fun onDestroyView() {
        homeAdapter = null
        super.onDestroyView()
    }
}