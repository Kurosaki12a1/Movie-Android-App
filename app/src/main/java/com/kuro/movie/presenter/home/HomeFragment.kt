package com.kuro.movie.presenter.home

import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentHomeBinding
import com.kuro.movie.extension.makeGone
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.presenter.home.adapter.NowPlayingRecyclerAdapter
import com.kuro.movie.presenter.home.adapter.PopularMoviesAdapter
import com.kuro.movie.presenter.home.adapter.PopularTvSeriesAdapter
import com.kuro.movie.presenter.home.adapter.TopRatedMoviesAdapter
import com.kuro.movie.presenter.home.adapter.TopRatedTvSeriesAdapter
import com.kuro.movie.presenter.home.adapter.paging_state.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import com.kuro.movie.presenter.home.adapter.paging_state.HandlePagingStateNowPlayingRecyclerAdapter
import com.kuro.movie.util.observerLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    inflater = FragmentHomeBinding::inflate
) {
    private val homeViewModel: HomeViewModel by viewModels()

    private val nowPlayingAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy { PopularMoviesAdapter() }
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter by lazy { PopularTvSeriesAdapter() }
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter by lazy { TopRatedMoviesAdapter() }
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter by lazy { TopRatedTvSeriesAdapter() }

    override fun onInitialize() {
        handlePagingLoadStates()
        addOnBackPressedCallBack(behavior = this::hideSeeAllPage)
        setupRecyclerAdapters()
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.btnNavigateUp.setOnClickListener {
            hideSeeAllPage()
        }

        binding.errorScreen.btnError.setOnClickListener {
            hideErrorScreenAndShowScrollView()
            nowPlayingAdapter.retry()
            popularMoviesAdapter.retry()
            popularTvSeriesAdapter.retry()
            topRatedMoviesAdapter.retry()
            topRatedTvSeriesAdapter.retry()
        }
        setAdaptersClickListener()
        setupListenerSeeAllClickEvents()
    }

    private fun setAdaptersClickListener() {
        popularMoviesAdapter.setOnItemClickListener { movie ->
            navigateToDetailBottomSheet(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = movie,
                    tvSeries = null
                )
            )
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            navigateToDetailBottomSheet(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = movie,
                    tvSeries = null
                )
            )
        }

        nowPlayingAdapter.setOnClickListener { movie ->
            navigateToDetailBottomSheet(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = movie,
                    tvSeries = null
                )
            )

        }

        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            navigateToDetailBottomSheet(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = tvSeries
                )
            )
        }

        topRatedTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            navigateToDetailBottomSheet(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = tvSeries
                )
            )
        }
    }

    private fun setupListenerSeeAllClickEvents() {
        binding.apply {
            nowPlayingSeeAll.setOnClickListener {
                homeViewModel.clickSeeAllText(getString(R.string.now_playing))
                recyclerViewSeeAll.adapter = nowPlayingAdapter
            }

            popularMoviesSeeAll.setOnClickListener {
                homeViewModel.clickSeeAllText(getString(R.string.popular_movies))
                recyclerViewSeeAll.adapter = popularMoviesAdapter
            }

            popularTvSeeAll.setOnClickListener {
                homeViewModel.clickSeeAllText(getString(R.string.popular_tv_series))
                recyclerViewSeeAll.adapter = popularTvSeriesAdapter
            }

            topRatedMoviesSeeAll.setOnClickListener {
                homeViewModel.clickSeeAllText(getString(R.string.top_rated_movies))
                recyclerViewSeeAll.adapter = topRatedMoviesAdapter
            }

            topRatedTvSeriesSeeAll.setOnClickListener {
                homeViewModel.clickSeeAllText(getString(R.string.top_rated_tv_series))
                recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
            }
        }

    }

    private fun handlePagingLoadStates() {
        HandlePagingStateNowPlayingRecyclerAdapter(
            nowPlayingRecyclerAdapter = nowPlayingAdapter,
            onLoading = binding.nowPlayingShimmerLayout::makeVisible,
            onNotLoading = binding.nowPlayingShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = popularMoviesAdapter,
            onLoading = binding.popularMoviesShimmerLayout::makeVisible,
            onNotLoading = binding.popularMoviesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = topRatedMoviesAdapter,
            onLoading = binding.topRatedMoviesShimmerLayout::makeVisible,
            onNotLoading = binding.topRatedMoviesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = popularTvSeriesAdapter,
            onLoading = binding.popularTvSeriesShimmerLayout::makeVisible,
            onNotLoading = binding.popularTvSeriesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = topRatedTvSeriesAdapter,
            onLoading = binding.topRatedTvSeriesShimmerLayout::makeVisible,
            onNotLoading = binding.topRatedTvSeriesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.fetchData()
        if (homeViewModel.homeState.value?.isShowsSeeAllPage == true) {
            val context = requireContext()
            val adapter =
                when (homeViewModel.homeState.value?.seeAllPageToolBarText) {
                    context.getString(R.string.now_playing) -> {
                        nowPlayingAdapter
                    }

                    context.getString(R.string.popular_movies) -> {
                        popularMoviesAdapter
                    }

                    context.getString(R.string.popular_tv_series) -> {
                        popularTvSeriesAdapter
                    }

                    context.getString(R.string.top_rated_movies) -> {
                        topRatedMoviesAdapter
                    }

                    context.getString(R.string.top_rated_tv_series) -> {
                        topRatedTvSeriesAdapter
                    }

                    else -> nowPlayingAdapter
                }
            binding.recyclerViewSeeAll.adapter = adapter
        }
    }

    private fun setUpObservers() {
        homeViewModel.homeState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                seeAllPage.isVisible = state.isShowsSeeAllPage
                scrollView.isVisible = !state.isShowsSeeAllPage
                if (state.isShowsSeeAllPage) {
                    showSeeAllPage(state.seeAllPageToolBarText)
                } else {
                    hideSeeAllPage()
                }
            }
        }

        observerLiveData(homeViewModel.nowPlayingMovie) {
            nowPlayingAdapter.submitData(it)
        }
        observerLiveData(homeViewModel.popularMovie) {
            popularMoviesAdapter.submitData(it)
        }
        observerLiveData(homeViewModel.topRatedMovie) {
            topRatedMoviesAdapter.submitData(it)
        }
        observerLiveData(homeViewModel.popularTvSeries) {
            popularTvSeriesAdapter.submitData(it)
        }
        observerLiveData(homeViewModel.topRatedTvSeries) {
            topRatedTvSeriesAdapter.submitData(it)
        }
    }

    private fun hideSeeAllPage() {
        binding.apply {
            scrollView.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            recyclerViewSeeAll.removeAllViews()
            seeAllPage.makeGone()
            scrollView.makeVisible()
        }
    }

    private fun setupRecyclerAdapters() {
        binding.apply {
            nowPlayingRecyclerView.adapter = nowPlayingAdapter
            nowPlayingRecyclerView.setAlpha(true)
            popularMoviesRecyclerView.adapter = popularMoviesAdapter
            topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
            popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
            topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter
        }
    }

    private fun hideScrollViewAndShowErrorScreen() {
        binding.scrollView.makeGone()
        binding.errorScreen.errorScreen.makeVisible()
    }

    private fun hideErrorScreenAndShowScrollView() {
        binding.errorScreen.errorScreen.makeGone()
        binding.scrollView.makeVisible()
    }

    private fun showSeeAllPage(text: String?) {
        binding.apply {
            seeAllPage.animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            text?.let { toolbarText.text = it }
        }
    }

    private fun navigateToDetailBottomSheet(navigateFlow: NavigateFlow.BottomSheetDetailFlow) {
        (requireActivity() as NavigationFlow).navigateToFlow(navigateFlow)
    }
}