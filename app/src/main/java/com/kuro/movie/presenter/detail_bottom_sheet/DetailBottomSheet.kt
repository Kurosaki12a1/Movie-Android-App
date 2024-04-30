package com.kuro.movie.presenter.detail_bottom_sheet

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.R
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.FragmentDetailBottomSheetBinding
import com.kuro.movie.extension.setAddFavoriteIconByFavoriteState
import com.kuro.movie.extension.setWatchListIconByWatchState
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil
import com.kuro.movie.util.observerLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        _binding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.apply {
            detailSection.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.NavigateToDetailFragment)
            }
            ibClose.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.Close)
            }
            btnFavoriteList.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.ClickedAddFavoriteList)
            }
            btnWatchingList.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.ClickedAddWatchList)
            }
        }
    }

    private fun setUpObservers() {
        observerLiveData(viewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is DetailBottomUiEvent.NavigateTo -> {
                    (requireActivity() as NavigationFlow).navigateToFlow(uiEvent.navigateFlow)
                }

                is DetailBottomUiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }

                is DetailBottomUiEvent.ShowSnackBar -> {
                    viewModel.sendMessage(uiEvent.text)
                }
            }
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.movie?.let {
                bindMovie(movie = it)
            }
            state.tvSeries?.let {
                bindTvSeries(tvSeries = it)
            }
            binding.btnFavoriteList.setAddFavoriteIconByFavoriteState(isFavorite = state.doesAddFavorite)
            binding.btnWatchingList.setWatchListIconByWatchState(isAddedWatchList = state.doesAddWatchList)
        }
    }

    private fun bindMovie(movie: Movie) {
        binding.apply {
            tvName.text = movie.title
            tvReleaseDate.text = movie.releaseDate
            tvOverview.text = movie.overview
            if (movie.posterPath != null) {
                loadImage(posterPath = movie.posterPath)
            }
            tvBottomInfoText.text =
                requireContext().getString(R.string.detail_bottom_sheet_movie_info)
        }
    }

    private fun bindTvSeries(tvSeries: TvSeries) {
        binding.apply {
            tvName.text = tvSeries.name
            tvOverview.text = tvSeries.overview
            tvReleaseDate.text = tvSeries.firstAirDate
            if (tvSeries.posterPath != null) {
                loadImage(posterPath = tvSeries.posterPath)
            }
            tvBottomInfoText.text =
                requireContext().getString(R.string.detail_bottom_sheet_tv_info)

            tvOverview.movementMethod = ScrollingMovementMethod()
        }
    }

    private fun loadImage(posterPath: String?) {
        binding.ivPoster.load(
            ImageUtil.getImage(
                imageSize = ImageSize.W185.path,
                imageUrl = posterPath
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}