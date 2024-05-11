package com.kuro.movie.presenter.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.kuro.movie.R
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.HomeCommonItemBinding
import com.kuro.movie.databinding.HomeNowPlayingItemBinding
import com.kuro.movie.extension.onLoading
import com.kuro.movie.extension.onNotLoading
import com.kuro.movie.presenter.home.HomeModel
import com.kuro.movie.presenter.home.adapter.paging_state.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import com.kuro.movie.presenter.home.adapter.paging_state.HandlePagingStateNowPlayingRecyclerAdapter
import com.kuro.movie.util.Constants.NOW_PLAYING
import com.kuro.movie.util.Constants.POPULAR_MOVIE
import com.kuro.movie.util.Constants.POPULAR_TV_SERIES
import com.kuro.movie.util.Constants.TOP_RATED_MOVIE
import com.kuro.movie.util.Constants.TOP_RATED_TV_SERIES
import kotlinx.coroutines.launch

class HomeAdapter(private val lifecycle: LifecycleCoroutineScope) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: HomeAdapterListener? = null

    private var items: ArrayList<HomeModel> = arrayListOf(
        HomeModel.NowPlayingItem(),
        HomeModel.PopularMoviesItem(),
        HomeModel.PopularTvSeriesItem(),
        HomeModel.TopRatedMoviesItem(),
        HomeModel.TopRatedTvSeriesItem()
    )

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeModel.NowPlayingItem -> R.layout.home_now_playing_item
            else -> R.layout.home_common_item
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeModel.NowPlayingItem -> {
                (holder as NowPlayingViewHolder).bind(
                    pagingData = item.pagingData,
                    title = item.title,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

            is HomeModel.PopularMoviesItem -> {
                (holder as CommonViewHolder).bind(
                    pagingMovie = item.pagingData,
                    title = item.title,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

            is HomeModel.TopRatedMoviesItem -> {
                (holder as CommonViewHolder).bind(
                    pagingMovie = item.pagingData,
                    title = item.title,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

            is HomeModel.PopularTvSeriesItem -> {
                (holder as CommonViewHolder).bind(
                    pagingTvSeries = item.pagingData,
                    title = item.title,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

            is HomeModel.TopRatedTvSeriesItem -> {
                (holder as CommonViewHolder).bind(
                    pagingTvSeries = item.pagingData,
                    title = item.title,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.home_now_playing_item -> {
                val binding = HomeNowPlayingItemBinding.inflate(layoutInflater, parent, false)
                NowPlayingViewHolder(binding)
            }

            else -> {
                val binding = HomeCommonItemBinding.inflate(layoutInflater, parent, false)
                CommonViewHolder(binding)
            }
        }
    }

    class NowPlayingViewHolder(private val binding: HomeNowPlayingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pagingData: PagingData<Movie>,
            title: String,
            lifecycle: LifecycleCoroutineScope,
            listener: HomeAdapterListener?
        ) {
            val adapter = NowPlayingRecyclerAdapter()
            binding.tvTitle.text = title
            binding.nowPlayingRecyclerView.setHasFixedSize(true)
            binding.nowPlayingRecyclerView.adapter = adapter
            binding.nowPlayingRecyclerView.setAlpha(true)
            binding.nowPlayingRecyclerView.setItemViewCacheSize(5)

            HandlePagingStateNowPlayingRecyclerAdapter(
                nowPlayingRecyclerAdapter = adapter,
                onLoading = binding.nowPlayingShimmerLayout::onLoading,
                onNotLoading = binding.nowPlayingShimmerLayout::onNotLoading,
                onError = { listener?.onError() })

            adapter.setOnClickListener {
                listener?.onItemClick(movie = it, categoryPosition = NOW_PLAYING)
            }

            binding.tvSeeAll.setOnClickListener {
                listener?.onSeeAllClick(title, NOW_PLAYING)
            }

            lifecycle.launch {
                adapter.submitData(pagingData)
            }
        }
    }

    class CommonViewHolder(private val binding: HomeCommonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pagingMovie: PagingData<Movie>? = null,
            pagingTvSeries: PagingData<TvSeries>? = null,
            title: String,
            lifecycle: LifecycleCoroutineScope,
            listener: HomeAdapterListener?
        ) {
            binding.tvTitle.text = title
            binding.commonRecyclerView.setHasFixedSize(true)
            binding.commonRecyclerView.setItemViewCacheSize(5)

            when (title) {
                binding.root.context.getString(R.string.popular_movies) -> {
                    val adapter = PopularMoviesAdapter()
                    binding.commonRecyclerView.adapter = adapter

                    HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(pagingAdapter = adapter,
                        onLoading = binding.commonShimmerLayout::onLoading,
                        onNotLoading = binding.commonShimmerLayout::onNotLoading,
                        onError = { listener?.onError() })

                    binding.tvSeeAll.setOnClickListener {
                        listener?.onSeeAllClick(title, POPULAR_MOVIE)
                    }

                    adapter.setOnItemClickListener {
                        listener?.onItemClick(movie = it, categoryPosition = POPULAR_MOVIE)
                    }

                    lifecycle.launch {
                        adapter.submitData(pagingMovie!!)
                    }
                }

                binding.root.context.getString(R.string.popular_tv_series) -> {
                    val adapter = PopularTvSeriesAdapter()
                    binding.commonRecyclerView.adapter = adapter

                    HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(pagingAdapter = adapter,
                        onLoading = binding.commonShimmerLayout::onLoading,
                        onNotLoading = binding.commonShimmerLayout::onNotLoading,
                        onError = { listener?.onError() })

                    binding.tvSeeAll.setOnClickListener {
                        listener?.onSeeAllClick(title, POPULAR_TV_SERIES)
                    }

                    adapter.setOnItemClickListener {
                        listener?.onItemClick(tvSeries = it, categoryPosition = POPULAR_TV_SERIES)
                    }

                    lifecycle.launch {
                        adapter.submitData(pagingTvSeries!!)
                    }
                }

                binding.root.context.getString(R.string.top_rated_movies) -> {
                    val adapter = TopRatedMoviesAdapter()
                    binding.commonRecyclerView.adapter = adapter

                    HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(pagingAdapter = adapter,
                        onLoading = binding.commonShimmerLayout::onLoading,
                        onNotLoading = binding.commonShimmerLayout::onNotLoading,
                        onError = { listener?.onError() })

                    binding.tvSeeAll.setOnClickListener {
                        listener?.onSeeAllClick(title, TOP_RATED_MOVIE)
                    }

                    adapter.setOnItemClickListener {
                        listener?.onItemClick(movie = it, categoryPosition = TOP_RATED_MOVIE)
                    }

                    lifecycle.launch {
                        adapter.submitData(pagingMovie!!)
                    }
                }

                binding.root.context.getString(R.string.top_rated_tv_series) -> {
                    val adapter = TopRatedTvSeriesAdapter()
                    binding.commonRecyclerView.adapter = adapter

                    HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(pagingAdapter = adapter,
                        onLoading = binding.commonShimmerLayout::onLoading,
                        onNotLoading = binding.commonShimmerLayout::onNotLoading,
                        onError = { listener?.onError() })

                    binding.tvSeeAll.setOnClickListener {
                        listener?.onSeeAllClick(title, TOP_RATED_TV_SERIES)
                    }

                    adapter.setOnItemClickListener {
                        listener?.onItemClick(tvSeries = it, categoryPosition = TOP_RATED_TV_SERIES)
                    }

                    lifecycle.launch {
                        adapter.submitData(pagingTvSeries!!)
                    }
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }

    fun setData(position: Int, data: HomeModel) {
        if (position in 0..4) {
            items[position] = data
            notifyItemChanged(position)
        }
    }

    fun setListener(listener: HomeAdapterListener) {
        this.listener = listener
    }

}

interface HomeAdapterListener {
    fun onError()
    fun onItemClick(movie: Movie? = null, tvSeries: TvSeries? = null, categoryPosition: Int)
    fun onSeeAllClick(title: String, categoryPosition: Int)
}
