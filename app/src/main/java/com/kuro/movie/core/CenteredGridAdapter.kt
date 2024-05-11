package com.kuro.movie.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kuro.movie.R
import com.kuro.movie.databinding.MovieRecommendationRowBinding
import com.kuro.movie.domain.model.MediaType
import com.kuro.movie.extension.makeVisible
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

abstract class CenteredGridAdapter<T : Any> :
    ListAdapter<T, CenteredGridAdapter.BaseListViewHolder>(DiffUtilCallBack<T>()) {
    var itemClickListener: (T) -> Unit = {}

    class BaseListViewHolder(
        private val binding: MovieRecommendationRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        fun bind(
            context: Context,
            posterPath: String?,
            movieTvName: String,
            voteAverage: String,
            voteCountByString: String,
            releaseDate: String?,
            genreByOne: String,
            mediaType: String
        ) {
            Glide.with(binding.ivPoster.context)
                .load(
                    ImageUtil.getImage(
                        imageSize = ImageSize.W185.path,
                        imageUrl = posterPath
                    )
                )
                .apply(requestOptions)
                .into(binding.ivPoster)

            binding.tvMovieTvName.text = movieTvName

            binding.txtCategory.makeVisible()

            bindTxtCategory(context, mediaType)

            releaseDate?.let {
                binding.tvReleaseDateGenre.text =
                    context.getString(R.string.release_date_genre, releaseDate, genreByOne)
            }
        }

        private fun bindTxtCategory(context: Context, mediaType: String) {
            when (mediaType) {
                MediaType.MOVIE.value -> {
                    binding.txtCategory.isVisible = true
                    binding.txtCategory.text = context.getString(R.string.movie)
                }

                MediaType.TV_SERIES.value -> {
                    binding.txtCategory.isVisible = true
                    binding.txtCategory.text = context.getString(R.string.tv)
                }

                else -> binding.txtCategory.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRecommendationRowBinding.inflate(layoutInflater, parent, false)
        return BaseListViewHolder(binding = binding)
    }


    fun setOnclickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }
}