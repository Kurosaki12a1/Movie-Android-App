package com.kuro.movie.presenter.library.adapter

import com.kuro.movie.core.BaseAdapter
import com.kuro.movie.data.model.Movie

class MovieAdapter : BaseAdapter<Movie>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = movie.posterPath,
            movieTvName = movie.title,
            voteAverage = movie.voteAverage.toString(),
            voteCountByString = movie.formattedVoteCount,
            releaseDate = movie.releaseDate,
            genreByOne = movie.genreByOne
        )

        holder.itemView.setOnClickListener { itemClickListener(movie) }
    }
}

