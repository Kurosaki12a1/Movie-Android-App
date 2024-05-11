package com.kuro.movie.presenter.detail.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.kuro.movie.R
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.ActorDetailItemBinding
import com.kuro.movie.databinding.InfoDetailItemBinding
import com.kuro.movie.databinding.PosterDetailItemBinding
import com.kuro.movie.databinding.VideoDetailItemBinding
import com.kuro.movie.databinding.WatchProviderDetailItemBinding
import com.kuro.movie.domain.model.CreatedBy
import com.kuro.movie.domain.model.Crew
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.model.WatchProviderItem
import com.kuro.movie.extension.makeGone
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.extension.setAddFavoriteIconByFavoriteState
import com.kuro.movie.extension.setWatchListIconByWatchState
import com.kuro.movie.presenter.detail.DetailModel
import com.kuro.movie.presenter.detail.helper.BindWatchProvidersHelper
import com.kuro.movie.presenter.detail.helper.inflater.CreatorTextInflater
import com.kuro.movie.presenter.detail.helper.inflater.DirectorTextInflater
import com.kuro.movie.util.Constants
import com.kuro.movie.util.ImageUtil
import kotlinx.coroutines.launch

class DetailAdapter(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val lifecycle: Lifecycle,
    private var listener: DetailAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items: ArrayList<DetailModel> = arrayListOf(
        DetailModel.Poster(),
        DetailModel.Info(),
        DetailModel.WatchProvider(),
        DetailModel.Actor(),
        DetailModel.VideosTab()
    )

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DetailModel.Poster -> R.layout.poster_detail_item
            is DetailModel.Info -> R.layout.info_detail_item
            is DetailModel.Actor -> R.layout.actor_detail_item
            is DetailModel.WatchProvider -> R.layout.watch_provider_detail_item
            else -> R.layout.video_detail_item
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.poster_detail_item -> {
                val binding = PosterDetailItemBinding.inflate(layoutInflater, parent, false)
                PosterViewHolder(binding)
            }

            R.layout.info_detail_item -> {
                val binding = InfoDetailItemBinding.inflate(layoutInflater, parent, false)
                InfoViewHolder(binding, listener)
            }

            R.layout.watch_provider_detail_item -> {
                val binding = WatchProviderDetailItemBinding.inflate(layoutInflater, parent, false)
                WatchProviderHolder(binding, listener)
            }

            R.layout.actor_detail_item -> {
                val binding = ActorDetailItemBinding.inflate(layoutInflater, parent, false)
                ActorViewHolder(binding, listener, lifecycleScope)
            }

            else -> {
                val binding = VideoDetailItemBinding.inflate(layoutInflater, parent, false)
                VideoViewHolder(binding, listener, lifecycle, lifecycleScope)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is DetailModel.Poster -> {
                (holder as PosterViewHolder).bind(item.posterPath)
            }

            is DetailModel.Info -> {
                (holder as InfoViewHolder).bind(
                    movieDetail = item.movieDetail,
                    tvDetail = item.tvDetail
                )
            }

            is DetailModel.WatchProvider -> {
                (holder as WatchProviderHolder).bind(
                    doesAddFavorite = item.doesAddFavorite,
                    doesAddWatchList = item.doesAddWatchList,
                    watchProviders = item.watchProviders
                )
            }

            is DetailModel.Actor -> {
                (holder as ActorViewHolder).bind(
                    movieDetail = item.movieDetail,
                    tvDetail = item.tvDetail
                )
            }

            is DetailModel.VideosTab -> {
                (holder as VideoViewHolder).bind(
                    movieRecommendation = item.movieRecommendation,
                    tvRecommendation = item.tvRecommendation,
                    videos = item.videos
                )
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is VideoViewHolder -> {
                holder.unBind()
            }

            is WatchProviderHolder -> {
                holder.unBind()
            }

            is ActorViewHolder -> {
                holder.unBind()
            }

            is InfoViewHolder -> {
                holder.unBind()
            }

            is PosterViewHolder -> {
                holder.unBind()
            }
        }
        super.onViewRecycled(holder)
    }

    class PosterViewHolder(private val binding: PosterDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CenterCrop())

        fun unBind() {
            Glide.with(binding.root.context)
                .clear(binding.imvPoster)
        }

        fun bind(posterPath: String) {
            Glide.with(binding.imvPoster.context)
                .load(ImageUtil.getImage(imageUrl = posterPath))
                .apply(requestOptions)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.posterShimmer.stopShimmer()
                        binding.posterShimmer.makeGone()
                        return false
                    }

                })
                .into(binding.imvPoster)
        }
    }

    class InfoViewHolder(
        private val binding: InfoDetailItemBinding,
        private val listener: DetailAdapterListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var creatorTextInflater: CreatorTextInflater? = null

        private var directorTextInflater: DirectorTextInflater? = null

        fun bind(movieDetail: MovieDetail? = null, tvDetail: TvDetail? = null) {
            setUpInflater()
            if (movieDetail != null) {
                bindMovieDetails(movieDetail)
            } else if (tvDetail != null) {
                bindTvDetails(tvDetail)
            } else {
                return
            }

            binding.creatorDirectorLinearLayout.setOnHierarchyChangeListener(
                object : ViewGroup.OnHierarchyChangeListener {
                    override fun onChildViewAdded(p0: View?, director: View?) {
                        director?.setOnClickListener {
                            listener?.onDirectorClick(director.id)
                        }
                    }

                    override fun onChildViewRemoved(p0: View?, p1: View?) {
                        return
                    }
                }
            )
        }

        private fun setUpInflater() {
            creatorTextInflater =
                CreatorTextInflater(
                    context = binding.root.context,
                    viewGroup = binding.creatorDirectorLinearLayout
                )

            directorTextInflater =
                DirectorTextInflater(
                    context = binding.root.context,
                    viewGroup = binding.creatorDirectorLinearLayout
                )
        }

        fun unBind() {
            creatorTextInflater = null
            directorTextInflater = null
        }

        private fun bindMovieDetails(movieDetail: MovieDetail) {
            removeDirectorsInLayout()
            bindDetailInfoSection(
                voteAverage = movieDetail.voteAverage,
                formattedVoteCount = movieDetail.formattedVoteCount,
                ratingBarValue = movieDetail.ratingValue,
                genresBySeparatedByComma = movieDetail.genresBySeparatedByComma
            )
            binding.txtFilmName.text = movieDetail.title
            binding.txtReleaseDate.text = movieDetail.releaseDate
            binding.txtOverview.text = movieDetail.overview
            bindMovieRuntime(convertedRuntime = movieDetail.convertedRuntime)
            bindDirectorName(crews = movieDetail.credit?.crew)
            binding.imvCircle.makeGone()
            binding.txtSeason.makeGone()
            binding.txtReleaseDate.text = movieDetail.releaseDate
            binding.txtRuntime.makeVisible()
            binding.imvClockIcon.makeVisible()

            binding.imvTmdb.setOnClickListener {
                val url = "${Constants.TMDB_MOVIE_URL}${movieDetail.id}"
                listener?.onTMDBClick(url)
            }
        }

        private fun bindTvDetails(tvDetail: TvDetail) {
            removeDirectorsInLayout()
            bindDetailInfoSection(
                voteAverage = tvDetail.voteAverage,
                formattedVoteCount = tvDetail.formattedVoteCount,
                ratingBarValue = tvDetail.ratingValue,
                genresBySeparatedByComma = tvDetail.genresBySeparatedByComma
            )
            binding.txtFilmName.text = tvDetail.name
            binding.txtReleaseDate.text = tvDetail.releaseDate
            binding.txtOverview.text = tvDetail.overview
            bindCreatorNames(createdBy = tvDetail.createdBy)
            showSeasonText(season = tvDetail.numberOfSeasons)
            binding.txtRuntime.visibility = View.GONE
            binding.imvClockIcon.visibility = View.GONE

            binding.imvTmdb.setOnClickListener {
                val url = "${Constants.TMDB_TV_URL}${tvDetail.id}"
                listener?.onTMDBClick(url)
            }
        }


        private fun bindMovieRuntime(convertedRuntime: Map<String, String>) {
            if (convertedRuntime.isNotEmpty()) {
                binding.txtRuntime.text = binding.txtRuntime.context.getString(
                    R.string.runtime,
                    convertedRuntime[Constants.HOUR_KEY],
                    convertedRuntime[Constants.MINUTES_KEY]
                )
            }
        }

        private fun bindDirectorName(crews: List<Crew>?) {
            if (!crews.isNullOrEmpty()) {
                binding.txtDirectorOrCreatorName.text =
                    binding.txtDirectorOrCreatorName.context.getString(R.string.director_title)
            }

            directorTextInflater?.createDirectorText(crews = crews)
        }

        private fun bindCreatorNames(createdBy: List<CreatedBy>?) {
            creatorTextInflater?.createCreatorTexts(
                createdByList = createdBy
            )
            setCreatorNameByCountOfCreator(creatorCount = createdBy?.count() ?: 0)
        }

        private fun setCreatorNameByCountOfCreator(creatorCount: Int) {
            binding.txtDirectorOrCreatorName.text = if (creatorCount > 1) {
                binding.txtDirectorOrCreatorName.context.getString(R.string.plural_creator_title)
            } else {
                binding.txtDirectorOrCreatorName.context.getString(R.string.singular_creator_title)
            }
        }

        private fun showSeasonText(season: Int) {
            binding.apply {
                imvCircle.makeVisible()
                txtSeason.makeVisible()
                txtSeason.text = txtSeason.context.getString(
                    R.string.season_count,
                    season.toString()
                )
            }
        }

        private fun removeDirectorsInLayout() {
            if (binding.creatorDirectorLinearLayout.childCount > 1) {
                binding.creatorDirectorLinearLayout.removeViewsInLayout(
                    1,
                    binding.creatorDirectorLinearLayout.childCount - 1
                )
            }
        }

        private fun bindDetailInfoSection(
            voteAverage: Double,
            formattedVoteCount: String,
            ratingBarValue: Float,
            genresBySeparatedByComma: String,
        ) {
            binding.apply {
                ratingBar.rating = ratingBarValue
                txtGenres.text = genresBySeparatedByComma
                txtVoteAverageCount.text = binding.txtVoteAverageCount.context.getString(
                    R.string.voteAverageDetail,
                    voteAverage.toString().subSequence(0, 3),
                    formattedVoteCount
                )
            }
        }
    }

    class WatchProviderHolder(
        private val binding: WatchProviderDetailItemBinding,
        private val listener: DetailAdapterListener?,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var watchProvidersHelper: BindWatchProvidersHelper? = null

        init {
            binding.btnFavoriteList.setOnClickListener {
                listener?.onClickFavorite()
            }

            binding.btnWatchList.setOnClickListener {
                listener?.onClickWatchList()
            }
        }

        fun unBind() {
            watchProvidersHelper = null
        }

        fun bind(
            doesAddFavorite: Boolean = false,
            doesAddWatchList: Boolean = false,
            watchProviders: WatchProviderItem? = null
        ) {
            watchProvidersHelper = BindWatchProvidersHelper(context = binding.root.context)
            binding.btnFavoriteList.setAddFavoriteIconByFavoriteState(isFavorite = doesAddFavorite)
            binding.btnWatchList.setWatchListIconByWatchState(isAddedWatchList = doesAddWatchList)

            watchProviders?.let { provider ->
                val streamWatchProviders = provider.stream
                val buyWatchProviders = provider.buy
                val rentWatchProviders = provider.rent

                watchProvidersHelper?.bind(
                    listOfWatchProviderItem = streamWatchProviders,
                    linearLayout = binding.imvStreamLayout,
                )
                watchProvidersHelper?.bind(
                    listOfWatchProviderItem = buyWatchProviders,
                    linearLayout = binding.imvBuyLayout,
                )
                watchProvidersHelper?.bind(
                    listOfWatchProviderItem = rentWatchProviders,
                    linearLayout = binding.imvRentLayout,
                )
            }
        }
    }


    class ActorViewHolder(
        private val binding: ActorDetailItemBinding,
        private val listener: DetailAdapterListener?,
        private val lifecycleScope: LifecycleCoroutineScope
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var adapter: DetailActorAdapter? = null

        init {
            binding.recyclerViewActor.setHasFixedSize(true)
        }

        fun bind(
            movieDetail: MovieDetail? = null,
            tvDetail: TvDetail? = null
        ) {
            setUpAdapter()
            val cast = movieDetail?.credit?.cast ?: tvDetail?.credit?.cast ?: arrayListOf()
            lifecycleScope.launch { adapter?.submitList(cast) }
        }

        fun unBind() {
            adapter = null
        }

        private fun setUpAdapter() {
            adapter = DetailActorAdapter()
            binding.recyclerViewActor.adapter = adapter
            adapter?.setActorTextListener { actorId ->
                listener?.onActorTextListener(actorId)
            }
        }
    }

    class VideoViewHolder(
        private val binding: VideoDetailItemBinding,
        private val listener: DetailAdapterListener?,
        private val lifecycle: Lifecycle,
        private val lifecycleScope: LifecycleCoroutineScope
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isRecommendationLoadingDone = false
        private var isVideoLoadingDone = false
        private var isNoVideo = false
        private var isMovie = false

        private var movieRecommendationAdapter: MovieRecommendationAdapter? = null
        private var tvRecommendationAdapter: TvRecommendationAdapter? = null
        private var videosAdapter: VideosAdapter? = null

        init {
            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: Tab?) {
                    handleTabSelection(tab?.position ?: Constants.TAB_RECOMMENDATION)
                }

                override fun onTabUnselected(p0: Tab?) {}

                override fun onTabReselected(p0: Tab?) {}
            })
        }

        private fun setUpViews() {
            movieRecommendationAdapter = MovieRecommendationAdapter()
            tvRecommendationAdapter = TvRecommendationAdapter()
            videosAdapter = VideosAdapter(lifecycle = lifecycle)
            setUpRecyclerViews()
            setUpListeners()
        }

        private fun setUpRecyclerViews() {
            binding.recommendationRecyclerView.setHasFixedSize(true)
            binding.recommendationRecyclerView.setItemViewCacheSize(10)

            binding.videosRecyclerView.setHasFixedSize(true)
        }

        private fun setUpListeners() {
            movieRecommendationAdapter?.setOnclickListener {
                listener?.onRecommendationClick(movie = it)
            }

            tvRecommendationAdapter?.setOnclickListener {
                listener?.onRecommendationClick(tvSeries = it)
            }
        }

        fun unBind() {
            movieRecommendationAdapter = null
            tvRecommendationAdapter = null
            videosAdapter = null
        }

        fun bind(
            movieRecommendation: List<Movie>? = null,
            tvRecommendation: List<TvSeries>? = null,
            videos: Videos? = null
        ) {
            setUpViews()
            isMovie = movieRecommendation?.isNotEmpty() ?: false
            isNoVideo = videos?.result?.isEmpty() ?: true
            handleTabSelection(binding.tabLayout.selectedTabPosition)

            if (movieRecommendation != null) {
                bindMovieRecommendations(movieRecommendation = movieRecommendation)
            } else if (tvRecommendation != null) {
                bindTvRecommendations(tvRecommendation = tvRecommendation)
            }

            if (videos != null) {
                bindVideos(videos = videos)
            }
        }

        private fun handleTabSelection(position: Int) {
            if (position == Constants.TAB_TRAILER) {
                binding.videosRecyclerView.isVisible = !isNoVideo
                binding.txtVideoInfo.isVisible = isNoVideo
                if (!isVideoLoadingDone) {
                    binding.videosShimmerLayout.makeVisible()
                }
                binding.recommendationShimmerLayout.makeGone()
                binding.recommendationRecyclerView.makeGone()

                binding.recommendationRecyclerView.adapter = null
                binding.videosRecyclerView.adapter = videosAdapter
            } else {
                if (!isRecommendationLoadingDone) {
                    binding.recommendationShimmerLayout.makeVisible()
                }
                binding.txtVideoInfo.makeGone()
                binding.recommendationRecyclerView.makeVisible()
                binding.videosShimmerLayout.makeGone()
                binding.videosRecyclerView.makeGone()

                binding.recommendationRecyclerView.adapter =
                    if (isMovie) movieRecommendationAdapter else tvRecommendationAdapter
                binding.videosRecyclerView.adapter = null
            }
        }

        private fun bindVideos(
            videos: Videos
        ) {
            videosAdapter?.submitList(videos.result) {
                isVideoLoadingDone = true
                binding.videosShimmerLayout.stopShimmer()
                binding.videosShimmerLayout.makeGone()
            }
        }

        private fun bindMovieRecommendations(
            movieRecommendation: List<Movie>
        ) {
            binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
            lifecycleScope.launch {
                movieRecommendationAdapter?.submitList(movieRecommendation) {
                    isRecommendationLoadingDone = true
                    binding.recommendationShimmerLayout.stopShimmer()
                    binding.recommendationShimmerLayout.makeGone()
                }
            }
        }

        private fun bindTvRecommendations(
            tvRecommendation: List<TvSeries>
        ) {
            binding.recommendationRecyclerView.adapter = tvRecommendationAdapter
            lifecycleScope.launch {
                tvRecommendationAdapter?.submitList(tvRecommendation) {
                    isRecommendationLoadingDone = true
                    binding.recommendationShimmerLayout.stopShimmer()
                    binding.recommendationShimmerLayout.makeGone()
                }
            }
        }
    }

    fun setData(position: Int, data: DetailModel) {
        if (position in 0..4) {
            if (!areItemsTheSame(items[position], data)) {
                items[position] = data
                notifyItemChanged(position)
            }
        }
    }
}

interface DetailAdapterListener {
    fun onActorTextListener(actorId: Int)
    fun onDirectorClick(directorId: Int)
    fun onRecommendationClick(movie: Movie? = null, tvSeries: TvSeries? = null)
    fun onTMDBClick(url: String)
    fun onClickFavorite()
    fun onClickWatchList()
}

fun areItemsTheSame(oldItem: DetailModel, newItem: DetailModel): Boolean {
    return when {
        oldItem is DetailModel.Poster && newItem is DetailModel.Poster -> oldItem.posterPath == newItem.posterPath
        oldItem is DetailModel.Info && newItem is DetailModel.Info -> (
                oldItem.movieDetail == newItem.movieDetail && oldItem.tvDetail == newItem.tvDetail
                )

        oldItem is DetailModel.WatchProvider && newItem is DetailModel.WatchProvider -> (
                oldItem.doesAddFavorite == newItem.doesAddFavorite &&
                        oldItem.doesAddWatchList == newItem.doesAddWatchList &&
                        oldItem.watchProviders == newItem.watchProviders
                )

        oldItem is DetailModel.Actor && newItem is DetailModel.Actor -> (
                oldItem.movieDetail == newItem.movieDetail && oldItem.tvDetail == newItem.tvDetail
                )

        oldItem is DetailModel.VideosTab && newItem is DetailModel.VideosTab -> (
                oldItem.movieRecommendation == newItem.movieRecommendation &&
                        oldItem.tvRecommendation == newItem.tvRecommendation &&
                        oldItem.videos == newItem.videos
                )

        else -> false
    }
}