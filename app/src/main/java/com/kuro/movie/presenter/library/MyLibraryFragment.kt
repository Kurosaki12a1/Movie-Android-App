package com.kuro.movie.presenter.library

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentMyLibraryBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.library.adapter.MovieAdapter
import com.kuro.movie.presenter.library.adapter.TvSeriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLibraryFragment : BaseFragment<FragmentMyLibraryBinding>(
    inflater = FragmentMyLibraryBinding::inflate
) {

    private val viewModel: MyLibraryViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val tvSeriesAdapter: TvSeriesAdapter by lazy { TvSeriesAdapter() }

    override fun onInitialize() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.listTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val chipType =
                if (group.checkedChipId == binding.movieChip.id) ChipType.MOVIE else ChipType.TV_SERIES
            viewModel.onEvent(MyLibraryEvent.UpdateListType(chipType = chipType))
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    val listTab =
                        if (tab.position == 0) LibraryTab.FAVORITE_LIST else LibraryTab.WATCH_LIST
                    viewModel.onEvent(MyLibraryEvent.SelectedTab(listTab))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.errorScreen.btnError.setOnClickListener {
            viewModel.fetchData()
        }
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            when (state.chipType) {
                ChipType.MOVIE -> {
                    movieAdapter.submitList(state.movieList)
                    binding.recyclerView.swapAdapter(movieAdapter, true)

                    movieAdapter.setOnclickListener { movie ->
                        navigateToFlow(NavigateFlow.BottomSheetDetailFlow(movie, null))
                    }
                }

                ChipType.TV_SERIES -> {
                    tvSeriesAdapter.submitList(state.tvSeriesList)
                    binding.recyclerView.swapAdapter(tvSeriesAdapter, true)
                    tvSeriesAdapter.setOnclickListener { tvSeries ->
                        navigateToFlow(NavigateFlow.BottomSheetDetailFlow(null, tvSeries))
                    }
                }
            }
            binding.errorScreen.errorScreen.isVisible = !state.hasAnyMovieInList
            binding.errorScreen.errorText.text = requireContext().getString(state.errorMessage)
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}