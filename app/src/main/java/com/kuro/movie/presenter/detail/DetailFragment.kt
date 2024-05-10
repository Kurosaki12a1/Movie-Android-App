package com.kuro.movie.presenter.detail

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.FragmentDetailBinding
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.presenter.detail.adapter.DetailAdapter
import com.kuro.movie.presenter.detail.adapter.DetailAdapterListener
import com.kuro.movie.presenter.detail.event.DetailEvent
import com.kuro.movie.presenter.detail.event.DetailUIEvent
import com.kuro.movie.util.Constants.ACTOR
import com.kuro.movie.util.Constants.INFO
import com.kuro.movie.util.Constants.POSTER
import com.kuro.movie.util.Constants.VIDEO
import com.kuro.movie.util.Constants.WATCH_PROVIDER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(
    inflater = FragmentDetailBinding::inflate
) {
    private val viewModel: DetailViewModel by viewModels()

    private var detailAdapter: DetailAdapter? = null

    private var detailStateObserver: Observer<DetailState>? = null
    private var consumableEventsObserver: Observer<List<DetailUIEvent>>? = null
    private var videosObserver: Observer<Videos?>? = null

    override fun onInitialize() {
        addOnBackPressedCallBack()
        setUpAdapter()
        setUpView()
        setUpObservers()
        onObservers()
    }

    private fun setUpAdapter() {
        detailAdapter = DetailAdapter(
            lifecycleScope = viewLifecycleOwner.lifecycleScope,
            lifecycle = lifecycle
        )
        detailAdapter?.setListener(object : DetailAdapterListener {
            override fun onActorTextListener(actorId: Int) {
                viewModel.onEvent(DetailEvent.ClickActorName(actorId = actorId))
            }

            override fun onDirectorClick(directorId: Int) {
                viewModel.onEvent(DetailEvent.ClickToDirectorName(directorId = directorId))
            }

            override fun onRecommendationClick(movie: Movie?, tvSeries: TvSeries?) {
                if (movie != null) {
                    viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(movie = movie))
                } else if (tvSeries != null) {
                    viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(tvSeries = tvSeries))
                }
            }

            override fun onTMDBClick(url: String) {
                viewModel.onEvent(DetailEvent.IntentToImdbWebSite(url = url))
            }

            override fun onClickFavorite() {
                viewModel.onEvent(DetailEvent.ClickedAddFavoriteList)
            }

            override fun onClickWatchList() {
                viewModel.onEvent(DetailEvent.ClickedAddWatchList)
            }

        })
        binding.recyclerViewDetail.adapter = detailAdapter
    }

    private fun setUpView() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onObservers() {
        viewModel.detailState.observe(viewLifecycleOwner, detailStateObserver!!)
        viewModel.consumableViewEvents.observe(viewLifecycleOwner, consumableEventsObserver!!)
        viewModel.videos.observe(viewLifecycleOwner, videosObserver!!)
    }

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        detailStateObserver = Observer { detailState ->
            binding.progressBar.isVisible = detailState.isLoading

            detailState.tvDetail?.let { detail ->
                binding.txtToolBarTitle.text = detail.name
                detailAdapter?.setData(
                    position = POSTER,
                    data = DetailModel.Poster(detail.posterPath ?: "")
                )

                detailAdapter?.setData(
                    position = INFO,
                    data = DetailModel.Info(tvDetail = detail)
                )

                detailAdapter?.setData(
                    position = WATCH_PROVIDER,
                    data = DetailModel.WatchProvider(
                        doesAddFavorite = detailState.doesAddFavorite,
                        doesAddWatchList = detailState.doesAddWatchList,
                        watchProviders = detail.watchProviders
                    )
                )

                detailAdapter?.setData(
                    position = ACTOR,
                    data = DetailModel.Actor(tvDetail = detail)
                )
            }

            detailState.movieDetail?.let { detail ->
                binding.txtToolBarTitle.text = detail.title
                detailAdapter?.setData(
                    position = POSTER,
                    data = DetailModel.Poster(detail.posterPath ?: "")
                )

                detailAdapter?.setData(
                    position = INFO,
                    data = DetailModel.Info(movieDetail = detail)
                )

                detailAdapter?.setData(
                    position = WATCH_PROVIDER,
                    data = DetailModel.WatchProvider(
                        doesAddFavorite = detailState.doesAddFavorite,
                        doesAddWatchList = detailState.doesAddWatchList,
                        watchProviders = detail.watchProviders
                    )
                )

                detailAdapter?.setData(
                    position = ACTOR,
                    data = DetailModel.Actor(movieDetail = detail)
                )
            }

            detailState.movieRecommendation?.let { movies ->
                val items = detailAdapter?.items?.get(VIDEO)
                items.let { value ->
                    detailAdapter?.setData(
                        position = VIDEO,
                        data = (value as DetailModel.VideosTab).copy(movieRecommendation = movies)
                    )
                }
            }

            detailState.tvRecommendation?.let { movies ->
                val items = detailAdapter?.items?.get(VIDEO)
                items.let { value ->
                    detailAdapter?.setData(
                        position = VIDEO,
                        data = (value as DetailModel.VideosTab).copy(tvRecommendation = movies)
                    )
                }
            }
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
            val items = detailAdapter?.items?.get(VIDEO)
            items.let { value ->
                detailAdapter?.setData(
                    position = VIDEO,
                    data = (value as DetailModel.VideosTab).copy(videos = videos)
                )
            }
            viewModel.videos.removeObserver(videosObserver!!)
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

    override fun onDestroyView() {
        detailAdapter = null
        detailStateObserver = null
        videosObserver = null
        consumableEventsObserver = null
        super.onDestroyView()
    }
}