package com.kuro.movie.presenter.explore.adapter.view_holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.kuro.movie.R
import com.kuro.movie.data.model.Movie
import com.kuro.movie.databinding.NowPlayingRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class SearchMovieViewHolder(
    private val binding: NowPlayingRowBinding,
    val context: Context
) : ViewHolder(binding.root) {

    fun bindMovie(
        movie: Movie,
        onMovieSearchItemClick: (Movie) -> Unit = {}
    ) {
        binding.backdropImage.load(
            ImageUtil.getImage(
                imageUrl = movie.posterPath,
                imageSize = ImageSize.W500.path
            )
        )


        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            movie.voteAverage.toString(),
            movie.formattedVoteCount
        )

        binding.genresText.text = movie.genreByOne

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = movie.title
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.movie)

        binding.root.setOnClickListener {
            onMovieSearchItemClick(movie)
        }
    }

    companion object {

        fun from(
            parent: ViewGroup
        ): SearchMovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchMovieViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}