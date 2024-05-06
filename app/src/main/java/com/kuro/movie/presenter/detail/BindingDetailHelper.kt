package com.kuro.movie.presenter.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.kuro.movie.databinding.FragmentDetailBinding
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.presenter.detail.adapter.DetailActorAdapter
import com.kuro.movie.presenter.detail.event.DetailEvent
import com.kuro.movie.presenter.detail.helper.BindMovieDetail
import com.kuro.movie.presenter.detail.helper.BindTvDetail
import com.kuro.movie.util.Constants
import com.kuro.movie.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView

class BindingDetailHelper(
    private val binding: FragmentDetailBinding,
    private val context: Context,
    private val viewModel: DetailViewModel,
    private val detailActorAdapter: DetailActorAdapter,
) {
    private val settingAdapterHelper: SettingAdapterHelper by lazy {
        SettingAdapterHelper()
    }

    init {
        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1500,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = context
        )

        setDirectorTextListener()

        setTmdbImageOnClickListener()

        addTabLayoutListener()

        setClickListeners()

        settingAdapterHelper.setupAdapters()
    }

    private fun setClickListeners() {
        binding.btnFavoriteList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddFavoriteList)
        }

        binding.btnWatchList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddWatchList)
        }
    }

    private fun setDirectorTextListener() {
        binding.creatorDirectorLinearLayout.setOnHierarchyChangeListener(
            object : ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(p0: View?, director: View?) {
                    director?.setOnClickListener {
                        viewModel.onEvent(
                            DetailEvent.ClickToDirectorName(directorId = director.id)
                        )
                    }
                }

                override fun onChildViewRemoved(p0: View?, p1: View?) {
                    return
                }
            }
        )
    }

    private fun setTmdbImageOnClickListener() {
        val detailState = viewModel.detailState.value
        binding.imvTmdb.setOnClickListener {
            val tmdbUrl = if (detailState?.tvId != null) {
                "${Constants.TMDB_TV_URL}${detailState.tvId}"
            } else if (detailState?.movieId != null) {
                "${Constants.TMDB_MOVIE_URL}${detailState.movieId}"
            } else {
                ""
            }
            viewModel.onEvent(DetailEvent.IntentToImdbWebSite(tmdbUrl))
        }
    }

    private fun addTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.recommendationRecyclerView.removeAllViews()
                    viewModel.onEvent(DetailEvent.SelectedTab(SelectableTab.entries[tab.position]))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun bindTvDetail(tvDetail: TvDetail?) {
        tvDetail?.let {
            BindTvDetail(
                binding = binding,
                context = context,
                tvDetail = tvDetail
            )
            detailActorAdapter.submitList(tvDetail.credit?.cast)
        }
    }

    fun bindMovieDetail(movieDetail: MovieDetail?) {
        movieDetail?.let {
            BindMovieDetail(
                binding = binding,
                context = context,
                movieDetail = movieDetail
            )
            detailActorAdapter.submitList(movieDetail.credit?.cast)
        }
    }

    inner class SettingAdapterHelper {
        fun setupAdapters() {
            setAdapterListener()
            setupDetailActorAdapter()
        }

        private fun setAdapterListener() {
            detailActorAdapter.setActorTextListener { actorId ->
                viewModel.onEvent(DetailEvent.ClickActorName(actorId = actorId))
            }
        }

        private fun setupDetailActorAdapter() {
            binding.recyclerViewActor.adapter = detailActorAdapter
        }
    }
}