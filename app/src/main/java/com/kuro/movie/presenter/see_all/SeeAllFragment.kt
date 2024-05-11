package com.kuro.movie.presenter.see_all

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSeeAllBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.home.adapter.NowPlayingRecyclerAdapter
import com.kuro.movie.presenter.home.adapter.PopularMoviesAdapter
import com.kuro.movie.presenter.home.adapter.PopularTvSeriesAdapter
import com.kuro.movie.presenter.home.adapter.TopRatedMoviesAdapter
import com.kuro.movie.presenter.home.adapter.TopRatedTvSeriesAdapter
import com.kuro.movie.util.observerLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllFragment : BaseFragment<FragmentSeeAllBinding>(
    inflater = FragmentSeeAllBinding::inflate
) {
    private val viewModel: SeeAllViewModel by viewModels()
    private var seeAllAdapter: PagingDataAdapter<*, *>? = null
    override fun onInitialize() {
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.recyclerViewSeeAll.setItemViewCacheSize(10)
        binding.recyclerViewSeeAll.setHasFixedSize(true)
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.toolbarText.text = state.title
            if (seeAllAdapter == null) {
                when (state.title) {
                    getString(R.string.now_playing) -> {
                        seeAllAdapter = NowPlayingRecyclerAdapter()
                        (seeAllAdapter as NowPlayingRecyclerAdapter).setOnClickListener {
                            navigateToFlow(
                                NavigateFlow.BottomSheetDetailFlow(
                                    movie = it,
                                    tvSeries = null
                                )
                            )
                        }
                    }

                    getString(R.string.popular_movies) -> {
                        seeAllAdapter = PopularMoviesAdapter()
                        (seeAllAdapter as PopularMoviesAdapter).setOnItemClickListener {
                            navigateToFlow(
                                NavigateFlow.BottomSheetDetailFlow(
                                    movie = it,
                                    tvSeries = null
                                )
                            )
                        }
                    }

                    getString(R.string.popular_tv_series) -> {
                        seeAllAdapter = PopularTvSeriesAdapter()
                        (seeAllAdapter as PopularTvSeriesAdapter).setOnItemClickListener {
                            navigateToFlow(
                                NavigateFlow.BottomSheetDetailFlow(
                                    movie = null,
                                    tvSeries = it
                                )
                            )
                        }
                    }

                    getString(R.string.top_rated_movies) -> {
                        seeAllAdapter = TopRatedMoviesAdapter()
                        (seeAllAdapter as TopRatedMoviesAdapter).setOnItemClickListener {
                            navigateToFlow(
                                NavigateFlow.BottomSheetDetailFlow(
                                    movie = it,
                                    tvSeries = null
                                )
                            )
                        }
                    }

                    getString(R.string.top_rated_tv_series) -> {
                        seeAllAdapter = TopRatedTvSeriesAdapter()
                        (seeAllAdapter as TopRatedTvSeriesAdapter).setOnItemClickListener {
                            navigateToFlow(
                                NavigateFlow.BottomSheetDetailFlow(
                                    movie = null,
                                    tvSeries = it
                                )
                            )
                        }
                    }
                }
                binding.recyclerViewSeeAll.adapter = seeAllAdapter
            }
        }

        observerLiveData(viewModel.movieData) { data ->
            if (data != null && isMovieAdapter()) {
                when (seeAllAdapter) {
                    is NowPlayingRecyclerAdapter -> {
                        (seeAllAdapter as? NowPlayingRecyclerAdapter)?.submitData(data)
                    }

                    is PopularMoviesAdapter -> {
                        (seeAllAdapter as PopularMoviesAdapter).submitData(data)
                    }

                    is TopRatedMoviesAdapter -> {
                        (seeAllAdapter as TopRatedMoviesAdapter).submitData(data)
                    }
                }
            }
        }

        observerLiveData(viewModel.tvSeriesData) { data ->
            if (data != null && isTvSeriesAdapter()) {
                when (seeAllAdapter) {
                    is PopularTvSeriesAdapter -> {
                        (seeAllAdapter as PopularTvSeriesAdapter).submitData(data)
                    }

                    is TopRatedTvSeriesAdapter -> {
                        (seeAllAdapter as TopRatedTvSeriesAdapter).submitData(data)
                    }
                }
            }
        }
    }

    private fun isMovieAdapter(): Boolean {
        return seeAllAdapter != null && (seeAllAdapter is NowPlayingRecyclerAdapter || seeAllAdapter is PopularMoviesAdapter || seeAllAdapter is TopRatedMoviesAdapter)
    }

    private fun isTvSeriesAdapter(): Boolean {
        return seeAllAdapter != null && (seeAllAdapter is PopularTvSeriesAdapter || seeAllAdapter is TopRatedTvSeriesAdapter)
    }

    override fun onDestroyView() {
        seeAllAdapter = null
        super.onDestroyView()
    }
}