package com.kuro.movie.presenter.explore

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.core.isEmpty
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.FragmentExploreBinding
import com.kuro.movie.domain.model.Category
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.repository.isAvailable
import com.kuro.movie.extension.makeGone
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.explore.adapter.FilterMoviesAdapter
import com.kuro.movie.presenter.explore.adapter.FilterTvSeriesAdapter
import com.kuro.movie.presenter.explore.adapter.SearchRecyclerAdapter
import com.kuro.movie.presenter.explore.adapter.paging_state.HandlePagingStateSearchAdapter
import com.kuro.movie.presenter.home.adapter.paging_state.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExploreFragment : BaseFragment<FragmentExploreBinding>(
    inflater = FragmentExploreBinding::inflate
) {
    private val viewModel: ExploreViewModel by activityViewModels()
    private var searchRecyclerAdapter: SearchRecyclerAdapter? = null
    private var movieFilterAdapter: FilterMoviesAdapter? = null
    private var tvFilterAdapter: FilterTvSeriesAdapter? = null
    private var searchJob: Job? = null

    private val queryObserver = Observer<String> { query ->
        binding.edtQuery.setSelection(query.length)
        viewModel.multiSearch(query)
        viewModel.multiSearch.observe(viewLifecycleOwner, searchObserver)
    }

    private val movieDiscoverObserver = Observer<PagingData<Movie>> { movieData ->
        viewLifecycleOwner.lifecycleScope.launch {
            hideTvAndSearchAdapters()
            cancelTvAndSearchJobs()
            binding.recyclerDiscoverMovie.makeVisible()
            movieFilterAdapter?.submitData(movieData)
        }
    }

    private val tvDiscoverObserver = Observer<PagingData<TvSeries>> { tvData ->
        viewLifecycleOwner.lifecycleScope.launch {
            cancelMovieAndSearchJobs()
            hideMoviesAndSearchAdapter()
            binding.recyclerDiscoverTv.makeVisible()
            tvFilterAdapter?.submitData(tvData)
        }
    }

    private val searchObserver = Observer<PagingData<MultiSearch>> { searchData ->
        viewLifecycleOwner.lifecycleScope.launch {
            cancelTvAndMovieJobs()
            hideTvAndMovieAdapters()
            binding.recyclerSearch.makeVisible()
            searchRecyclerAdapter?.submitData(searchData)
        }
    }

    override fun onInitialize() {
        setUpAdapters()
        observeConnectivityStatus()
        setUpView()
        handlePagingLoadStates()
    }

    private fun setUpAdapters() {
        searchRecyclerAdapter = SearchRecyclerAdapter()
        movieFilterAdapter = FilterMoviesAdapter()
        tvFilterAdapter = FilterTvSeriesAdapter()
    }

    private fun setUpView() {
        setupAdapters()
        addTextChangedListener()
        setClickListeners()
    }

    private fun onObservers() {
        viewModel.filterBottomSheetState.observe(viewLifecycleOwner) { filterBottomState ->
            when (filterBottomState.categoryState) {
                Category.MOVIE -> {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                    viewModel.discoverMovie.observe(viewLifecycleOwner, movieDiscoverObserver)
                }

                Category.TV -> {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                    viewModel.discoverTv.observe(viewLifecycleOwner, tvDiscoverObserver)
                }

                Category.SEARCH -> {
                    viewModel.query.observe(viewLifecycleOwner, queryObserver)
                }
            }
        }
    }

    private fun setupAdapters() {
        binding.recyclerSearch.adapter = searchRecyclerAdapter
        binding.recyclerDiscoverMovie.adapter = movieFilterAdapter
        binding.recyclerDiscoverTv.adapter = tvFilterAdapter
    }

    private fun addTextChangedListener() {
        binding.edtQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (viewModel.query.hasObservers()) {
                    viewModel.query.removeObserver(queryObserver)
                }
                searchJob?.cancel()

                // Debounce 300ms
                searchJob = lifecycleScope.launch {
                    delay(300)
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.MultiSearch(query = s.toString()))
                }
            }
        })
    }

    private fun setClickListeners() {
        binding.filter.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.OpenFilter)
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment()
            )
        }

        binding.errorScreen.btnError.setOnClickListener {
            if (viewModel.isNetworkAvailable()) {
                onObservers()
                hideErrorScreenAndShowDetailScreen()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.internet_error), Toast.LENGTH_SHORT
                ).show()
            }
        }

        searchRecyclerAdapterListeners()
    }

    private fun searchRecyclerAdapterListeners() {
        setupSearchRecyclerAdapterListener()
        movieFilterAdapter?.setOnItemClickListener { movie ->
            navigateToFlow(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = movie,
                    tvSeries = null
                )
            )
        }
        tvFilterAdapter?.setOnItemClickListener { tvSeries ->
            navigateToFlow(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = tvSeries
                )
            )
        }
    }

    private fun setupSearchRecyclerAdapterListener() {
        searchRecyclerAdapter?.setOnTvSearchClickListener { tvSeries ->
            navigateToFlow(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = tvSeries
                )
            )
        }
        searchRecyclerAdapter?.setOnMovieSearchClickListener { movie ->
            navigateToFlow(
                NavigateFlow.BottomSheetDetailFlow(
                    movie = movie,
                    tvSeries = null
                )
            )
        }
        searchRecyclerAdapter?.setOnPersonSearchClickListener { person ->
            navigateToFlow(NavigateFlow.PersonDetailFlow(person.id))
        }
    }


    private fun observeConnectivityStatus() {
        viewModel.networkState.observe(viewLifecycleOwner) { networkState ->
            if (networkState?.isAvailable() == true) {
                hideErrorScreenAndShowDetailScreen()
                onObservers()
            } else {
                if (isAdaptersEmpty()) {
                    showErrorScreenAndHideDetailScreen()
                }
            }
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = tvFilterAdapter!!,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerDiscoverTv.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerDiscoverTv.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = movieFilterAdapter!!,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerDiscoverMovie.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerDiscoverMovie.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )

        HandlePagingStateSearchAdapter(
            searchPagingAdapter = searchRecyclerAdapter!!,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerSearch.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerSearch.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )
    }

    private fun isAdaptersEmpty(): Boolean {
        return movieFilterAdapter?.isEmpty() == true || tvFilterAdapter?.isEmpty() == true
                || searchRecyclerAdapter?.itemCount!! <= 0
    }

    private fun showErrorScreenAndHideDetailScreen() {
        binding.detailScreen.makeGone()
        binding.errorScreen.errorScreen.makeVisible()
    }

    private fun hideErrorScreenAndShowDetailScreen() {
        binding.detailScreen.makeVisible()
        binding.errorScreen.errorScreen.makeGone()
    }

    private fun cancelMovieAndSearchJobs() {
        viewModel.multiSearch.removeObserver(searchObserver)
        viewModel.discoverMovie.removeObserver(movieDiscoverObserver)
    }

    private fun cancelTvAndSearchJobs() {
        viewModel.multiSearch.removeObserver(searchObserver)
        viewModel.discoverTv.removeObserver(tvDiscoverObserver)
    }

    private fun cancelTvAndMovieJobs() {
        viewModel.discoverMovie.removeObserver(movieDiscoverObserver)
        viewModel.discoverTv.removeObserver(tvDiscoverObserver)
    }

    private fun hideMoviesAndSearchAdapter() {
        binding.recyclerDiscoverMovie.makeGone()
        binding.recyclerSearch.makeGone()
    }

    private fun hideTvAndSearchAdapters() {
        binding.recyclerSearch.makeGone()
        binding.recyclerDiscoverTv.makeGone()
    }

    private fun hideTvAndMovieAdapters() {
        binding.recyclerDiscoverTv.makeGone()
        binding.recyclerDiscoverMovie.makeGone()
    }

    override fun onDestroyView() {
        searchRecyclerAdapter = null
        movieFilterAdapter = null
        tvFilterAdapter = null
        super.onDestroyView()
    }

}