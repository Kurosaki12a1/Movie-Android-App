package com.kuro.movie.presenter.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kuro.movie.R
import com.kuro.movie.core.DiffUtilCallBack
import com.kuro.movie.data.model.Movie
import com.kuro.movie.databinding.NowPlayingRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class NowPlayingRecyclerAdapter :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack()) {

    private var onItemClickListener: (Movie) -> Unit = {}

    class MovieViewHolder(
        private val binding: NowPlayingRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context, onItemClickListener: (Movie) -> Unit = {}) {
            binding.movieTitle.text = movie.title
            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(),
                movie.formattedVoteCount
            )

            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(context)
                .load(
                    ImageUtil.getImage(
                        imageUrl = movie.posterPath,
                        imageSize = ImageSize.W500.path
                    )
                )
                .apply(requestOptions)
                .into(binding.backdropImage)

            binding.genresText.text = movie.genresBySeparatedByComma

            binding.root.setOnClickListener {
                onItemClickListener.invoke(movie)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            val context = holder.itemView.context
            holder.bind(movie = movie, context = context, onItemClickListener = onItemClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding = binding)
    }

    fun setOnClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

}