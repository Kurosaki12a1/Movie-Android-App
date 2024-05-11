package com.kuro.movie.presenter.detail.adapter

import com.kuro.movie.core.CenteredGridAdapter
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.model.MediaType

class MovieRecommendationAdapter : CenteredGridAdapter<Movie>() {
    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = movie.posterPath,
            movieTvName = movie.title,
            voteAverage = movie.voteAverage.toString(),
            voteCountByString = movie.formattedVoteCount,
            releaseDate = movie.releaseDate,
            genreByOne = movie.genreByOne,
            mediaType = MediaType.MOVIE.value
        )

        holder.itemView.setOnClickListener { itemClickListener(movie) }
    }
}