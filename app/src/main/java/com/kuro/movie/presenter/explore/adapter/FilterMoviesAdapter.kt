package com.kuro.movie.presenter.explore.adapter

import android.content.Context
import android.view.View
import coil.load
import com.kuro.movie.R
import com.kuro.movie.core.BaseMovieAndTvRecyclerAdapter
import com.kuro.movie.data.model.Movie
import com.kuro.movie.databinding.MovieRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class FilterMoviesAdapter : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(
        binding: MovieRowBinding,
        position: Int,
        context: Context
    ) {

        val movie = getItem(position)

        if (movie != null) {
            binding.ivPoster.load(
                ImageUtil.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = movie.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(R.string.movie)
        }
    }
}
