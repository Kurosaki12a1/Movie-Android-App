package com.kuro.movie.presenter.home.adapter

import androidx.paging.PagingDataAdapter
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

interface HomeAdapterListener {
    fun onError()
    fun onItemClick(movie: Movie? = null, tvSeries: TvSeries? = null, categoryPosition: Int)
    fun onSeeAllClick(title: String, categoryPosition: Int)
}