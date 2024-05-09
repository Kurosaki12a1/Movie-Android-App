package com.kuro.movie.presenter.home.adapter

import android.content.Context
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kuro.movie.core.BaseMovieAndTvRecyclerAdapter
import com.kuro.movie.data.model.Movie
import com.kuro.movie.databinding.MovieRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class PopularMoviesAdapter : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(
        binding: MovieRowBinding,
        position: Int,
        context: Context
    ) {

        val movie = getItem(position)
        if (movie != null) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(context)
                .load(
                    ImageUtil.getImage(
                        imageSize = ImageSize.W185.path,
                        imageUrl = movie.posterPath
                    )
                )
                .apply(requestOptions)
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
        }

    }
}