package com.kuro.movie.presenter.detail

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentDetailBinding
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.extension.makeGone
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.extension.setAddFavoriteIconByFavoriteState
import com.kuro.movie.extension.setWatchListIconByWatchState
import com.kuro.movie.presenter.detail.adapter.DetailActorAdapter
import com.kuro.movie.presenter.detail.adapter.VideosAdapter
import com.kuro.movie.presenter.detail.event.DetailEvent
import com.kuro.movie.presenter.detail.event.DetailUIEvent
import com.kuro.movie.presenter.library.adapter.MovieAdapter
import com.kuro.movie.presenter.library.adapter.TvSeriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(
    inflater = FragmentDetailBinding::inflate
) {
    private val viewModel: DetailViewModel by viewModels()

    private var movieRecommendationAdapter: MovieAdapter? = null
    private var detailActorAdapter: DetailActorAdapter? = null
    private var tvRecommendationAdapter: TvSeriesAdapter? = null
    private var videosAdapter: VideosAdapter? = null

    private var bindingDetailHelper: BindingDetailHelper? = null
    private var detailStateObserver: Observer<DetailState>? = null
    private var consumableEventsObserver: Observer<List<DetailUIEvent>>? = null
    private var videosObserver: Observer<Videos?>? = null

    override fun onInitialize() {
        setUpAdapters()
        setUpView()
        addOnBackPressedCallBack()
        setUpObservers()
        onObservers()
    }

    private fun setUpAdapters() {
        movieRecommendationAdapter = MovieAdapter()
        detailActorAdapter = DetailActorAdapter()
        tvRecommendationAdapter = TvSeriesAdapter()
        videosAdapter = VideosAdapter(viewLifecycleOwner.lifecycle)
    }

    private fun setUpView() {
        bindingDetailHelper = BindingDetailHelper(
            binding = binding,
            context = requireContext(),
            viewModel = viewModel,
            detailActorAdapter = detailActorAdapter!!
        )

        binding.recommendationRecyclerView.setHasFixedSize(true)
        binding.videosRecyclerView.setHasFixedSize(true)

        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.detailState.removeObserver(detailStateObserver!!)
            viewModel.consumableViewEvents.removeObserver(consumableEventsObserver!!)
            onObservers()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        movieRecommendationAdapter?.setOnclickListener { movie ->
            viewModel.onEvent(
                DetailEvent.ClickRecommendationItemClick(
                    movie = movie
                )
            )
        }

        tvRecommendationAdapter?.setOnclickListener { tvSeries ->
            viewModel.onEvent(
                DetailEvent.ClickRecommendationItemClick(
                    tvSeries = tvSeries
                )
            )
        }

        binding.swipeRefreshLayout.isEnabled = false
    }

    private fun onObservers() {
        viewModel.detailState.observe(viewLifecycleOwner, detailStateObserver!!)
        viewModel.consumableViewEvents.observe(viewLifecycleOwner, consumableEventsObserver!!)
    }

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        detailStateObserver = Observer { detailState ->
            binding.progressBar.isVisible = detailState.isLoading

            if (detailState.isSelectedTrailerTab()) {
                binding.recommendationRecyclerView.makeGone()
                binding.videosRecyclerView.makeVisible()
                viewModel.videos.observe(viewLifecycleOwner, videosObserver!!)
            } else {
                viewModel.videos.removeObserver(videosObserver!!)
                binding.txtVideoInfo.makeGone()
                binding.videosRecyclerView.makeGone()
                binding.recommendationRecyclerView.makeVisible()
            }

            detailState.tvDetail?.let {
                bindingDetailHelper?.bindTvDetail(detailState.tvDetail)
                if (detailState.isSelectedRecommendationTab()) {
                    binding.recommendationRecyclerView.swapAdapter(tvRecommendationAdapter, true)
                }
            }

            detailState.movieDetail?.let {
                bindingDetailHelper?.bindMovieDetail(detailState.movieDetail)
                if (detailState.isSelectedRecommendationTab()) {
                    binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
                }
            }

            detailState.movieRecommendation?.let { movieRecommendation ->
                movieRecommendationAdapter?.submitList(movieRecommendation)
            }

            detailState.tvRecommendation?.let {
                tvRecommendationAdapter?.submitList(it)
            }

            binding.btnFavoriteList.setAddFavoriteIconByFavoriteState(isFavorite = detailState.doesAddFavorite)
            binding.btnWatchList.setWatchListIconByWatchState(isAddedWatchList = detailState.doesAddWatchList)

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading
            binding.videosShimmerLayout.isVisible = detailState.videosLoading
        }

        consumableEventsObserver = Observer { listOfUiEvents ->
            if (listOfUiEvents.isNotEmpty()) {
                when (val uiEvent = listOfUiEvents.first()) {
                    is DetailUIEvent.PopBackStack -> {
                        findNavController().popBackStack()
                        viewModel.onEventConsumed()
                    }

                    is DetailUIEvent.ShowSnackBar -> {
                        binding.swipeRefreshLayout.isEnabled = true
                        viewModel.sendMessage(uiEvent.text)
                        viewModel.onEventConsumed()
                    }

                    is DetailUIEvent.IntentToImdbWebSite -> {
                        intentToTMDBWebSite(uiEvent.url)
                        viewModel.onEventConsumed()
                    }

                    is DetailUIEvent.NavigateTo -> {
                        navigateToFlow(uiEvent.navigateFlow)
                        viewModel.onEventConsumed()
                    }

                }
            }
        }

        videosObserver = Observer { videos ->
            if (videos == null) return@Observer
            binding.videosRecyclerView.isVisible = videos.result.isNotEmpty()
            binding.txtVideoInfo.isVisible = videos.result.isEmpty()
            videosAdapter?.submitList(videos.result)
        }

    }

    private fun intentToTMDBWebSite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity((requireContext().packageManager)) != null) {
            startActivity(intent)
        } else {
            viewModel.sendMessage("Cannot open this url")
        }
    }

    private fun cleanUp() {
        bindingDetailHelper?.cleanUp()
        movieRecommendationAdapter = null
        detailActorAdapter = null
        tvRecommendationAdapter = null
        videosAdapter = null

        bindingDetailHelper = null
        detailStateObserver = null
        consumableEventsObserver = null
        videosObserver = null
    }

    override fun onDestroyView() {
        cleanUp()
        super.onDestroyView()
    }
}