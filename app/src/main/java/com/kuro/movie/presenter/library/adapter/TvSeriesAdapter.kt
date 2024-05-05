package com.kuro.movie.presenter.library.adapter

import com.kuro.movie.core.BaseAdapter
import com.kuro.movie.data.model.TvSeries

class TvSeriesAdapter : BaseAdapter<TvSeries>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = tvSeries.posterPath,
            movieTvName = tvSeries.name,
            voteAverage = tvSeries.voteAverage.toString(),
            voteCountByString = tvSeries.formattedVoteCount,
            releaseDate = tvSeries.firstAirDate,
            genreByOne = tvSeries.genreByOne
        )

        holder.itemView.setOnClickListener {
            itemClickListener(tvSeries)
        }
    }
}