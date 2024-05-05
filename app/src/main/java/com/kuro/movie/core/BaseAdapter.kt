package com.kuro.movie.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.movie.R
import com.kuro.movie.databinding.MovieRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

abstract class BaseAdapter<T : Any> :
    ListAdapter<T, BaseAdapter.BaseListViewHolder>(DiffUtilCallBack<T>()) {
    var itemClickListener: (T) -> Unit = {}

    class BaseListViewHolder(
        private val binding: MovieRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            context: Context,
            posterPath: String?,
            movieTvName: String,
            voteAverage: String,
            voteCountByString: String,
            releaseDate: String?,
            genreByOne: String
        ) {
            binding.ivPoster.load(
                ImageUtil.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = posterPath
                )
            )
            binding.tvMovieTvName.text = movieTvName

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                voteAverage,
                voteCountByString
            )

            releaseDate?.let {
                binding.tvReleaseDateGenre.text =
                    context.getString(R.string.release_date_genre, releaseDate, genreByOne)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return BaseListViewHolder(
            binding = binding
        )
    }


    fun setOnclickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }
}