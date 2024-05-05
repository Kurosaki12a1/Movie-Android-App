package com.kuro.movie.presenter.up_coming.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.movie.R
import com.kuro.movie.databinding.ComingSoonItemBinding
import com.kuro.movie.domain.model.UpComingMovie
import com.kuro.movie.util.DateFormatUtils
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class UpComingMovieAdapter :
    PagingDataAdapter<UpComingMovie, UpComingMovieAdapter.UpComingViewHolder>(
        UpComingDiffUtilCallback()
    ) {
    private var infoListener: (UpComingMovie) -> Unit = {}
    private var remindMeListener: (UpComingMovie) -> Unit = {}

    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        val upComingMovie = getItem(position)

        upComingMovie?.let { holder.bind(upComingMovie = it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpComingViewHolder {
        val binding = ComingSoonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpComingViewHolder(
            binding = binding
        )
    }

    fun setOnInfoClickListener(listener: (UpComingMovie) -> Unit) {
        this.infoListener = listener
    }

    fun setOnRemindMeClickListener(listener: (UpComingMovie) -> Unit) {
        this.remindMeListener = listener
    }

    inner class UpComingViewHolder(
        val binding: ComingSoonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(upComingMovie: UpComingMovie) {
            val movie = upComingMovie.movie
            bindImages(posterPath = movie.posterPath)

            bindBody(
                movieTitle = movie.title,
                movieOverview = movie.overview,
                movieReleaseDate = movie.fullReleaseDate ?: "",
                movieGenres = movie.genresBySeparatedByComma
            )

            bindRemindMeIcons(isAddedToRemind = upComingMovie.isAddedToRemind)

            setupClickListeners(listener = upComingMovie)
        }

        private fun bindImages(posterPath: String?) {
            binding.imvMoviePoster.load(
                ImageUtil.getImage(
                    imageSize = ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
            binding.imvMovieBackdrop.load(
                ImageUtil.getImage(
                    imageSize = ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
        }

        private fun bindBody(
            movieTitle: String,
            movieOverview: String,
            movieReleaseDate: String,
            movieGenres: String
        ) {

            binding.txtMovieName.text = movieTitle

            binding.txtOverview.text = movieOverview

            binding.txtReleaseDate.text =
                DateFormatUtils.convertDateFormat(movieReleaseDate)

            binding.txtGenre.text = movieGenres

        }

        private fun bindRemindMeIcons(
            isAddedToRemind: Boolean
        ) {
            val remindMeIconRes = if (isAddedToRemind) {
                R.drawable.ic_remind_me_added_white
            } else {
                R.drawable.ic_remind_me_white
            }

            binding.imvRemindMe.setImageResource(remindMeIconRes)
        }

        private fun setupClickListeners(listener: UpComingMovie) {
            binding.imvInfo.setOnClickListener {
                infoListener(listener)
            }

            binding.imvRemindMe.setOnClickListener {
                remindMeListener(listener)
            }
        }
    }
}

class UpComingDiffUtilCallback : DiffUtil.ItemCallback<UpComingMovie>() {
    override fun areItemsTheSame(oldItem: UpComingMovie, newItem: UpComingMovie): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(oldItem: UpComingMovie, newItem: UpComingMovie): Boolean {
        return oldItem == newItem
    }
}
