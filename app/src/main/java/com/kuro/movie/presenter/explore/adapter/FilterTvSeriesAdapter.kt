package com.kuro.movie.presenter.explore.adapter

import android.content.Context
import android.view.View
import coil.load
import com.kuro.movie.R
import com.kuro.movie.core.BaseMovieAndTvRecyclerAdapter
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.MovieRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class FilterTvSeriesAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

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

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(R.string.tv)
        }
    }

}