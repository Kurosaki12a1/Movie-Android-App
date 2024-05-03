package com.kuro.movie.presenter.explore.adapter.view_holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.kuro.movie.R
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.databinding.NowPlayingRowBinding
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil


class SearchTvViewHolder(
    val binding: NowPlayingRowBinding,
    val context: Context
) : ViewHolder(binding.root) {

    fun bindSearchTv(
        tvSeries: TvSeries,
        onSearchTvItemClick: (TvSeries) -> Unit = {}
    ) {
        binding.backdropImage.load(
            ImageUtil.getImage(
                imageUrl = tvSeries.posterPath,
                imageSize = ImageSize.W500.path
            )
        )

        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            tvSeries.voteAverage.toString(),
            tvSeries.formattedVoteCount
        )

        binding.genresText.text = tvSeries.genreByOne

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = tvSeries.name
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.tv)

        binding.root.setOnClickListener {
            onSearchTvItemClick(tvSeries)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchTvViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchTvViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}