package com.kuro.movie.presenter.detail.adapter

import android.content.Context
import coil.load
import com.kuro.movie.core.BaseMovieAndTvRecyclerAdapter
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.MovieRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class TvRecommendationAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageUtil.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
        }
    }
}