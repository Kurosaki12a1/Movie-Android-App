package com.kuro.movie.presenter.detail.adapter

import com.kuro.movie.core.CenteredGridAdapter
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.MediaType

class TvRecommendationAdapter : CenteredGridAdapter<TvSeries>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = tvSeries.posterPath,
            movieTvName = tvSeries.name,
            voteAverage = tvSeries.voteAverage.toString(),
            voteCountByString = tvSeries.formattedVoteCount,
            releaseDate = tvSeries.firstAirDate,
            genreByOne = tvSeries.genreByOne,
            mediaType = MediaType.TV_SERIES.value
        )

        holder.itemView.setOnClickListener {
            itemClickListener(tvSeries)
        }
    }

    override fun onViewRecycled(holder: BaseListViewHolder) {
        holder.unBind()
        super.onViewRecycled(holder)
    }
}