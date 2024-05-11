package com.kuro.movie.presenter.up_coming

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentUpComingBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.up_coming.adapter.UpComingMovieAdapter
import com.kuro.movie.presenter.up_coming.adapter.paging_state.HandlePagingStateUpComingPagingAdapter
import com.kuro.movie.util.observerLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingFragment : BaseFragment<FragmentUpComingBinding>(
    inflater = FragmentUpComingBinding::inflate
) {
    private val viewModel: UpComingViewModel by viewModels()
    private var upComingMovieAdapter : UpComingMovieAdapter? = null

    override fun onInitialize() {
        setUpViews()
        setUpObservers()
        handlePagingLoadStates()
    }

    private fun setUpViews() {
        upComingMovieAdapter = UpComingMovieAdapter()
        binding.upComingRecyclerView.adapter = upComingMovieAdapter

        upComingMovieAdapter?.setOnInfoClickListener { upComingMovie ->
            val action = NavigateFlow.BottomSheetDetailFlow(
                movie = upComingMovie.movie,
                tvSeries = null
            )
            navigateToFlow(action)
        }

        upComingMovieAdapter?.setOnRemindMeClickListener { upComingMovie ->
            viewModel.onEvent(UpComingEvent.OnClickRemindMe(upComingMovie))
        }
    }

    private fun setUpObservers() {
        observerLiveData(viewModel.upcomingMovies) {
            upComingMovieAdapter?.submitData(it)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.upComingRecyclerView.isVisible = !state.isLoading

            binding.errorTextView.isVisible = state.error.isNotEmpty()
            binding.errorTextView.text = state.error
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingStateUpComingPagingAdapter(
            upComingPagingAdapter = upComingMovieAdapter!!,
            onLoading = {
                viewModel.onEvent(UpComingEvent.Loading)
            },
            onError = { error ->
                viewModel.onEvent(UpComingEvent.Error(error))
            },
            onNotLoading = {
                viewModel.onEvent(UpComingEvent.NotLoading)
            }
        )
    }

    override fun onDestroyView() {
        upComingMovieAdapter = null
        super.onDestroyView()
    }
}