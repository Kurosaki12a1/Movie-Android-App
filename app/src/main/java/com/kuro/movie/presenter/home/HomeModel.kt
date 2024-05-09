package com.kuro.movie.presenter.home

import androidx.paging.PagingData
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

sealed class HomeModel {
    data class NowPlayingItem(
        val pagingData: PagingData<Movie> = PagingData.empty(),
        val title: String = ""
    ) : HomeModel()

    data class PopularMoviesItem(
        val pagingData: PagingData<Movie> = PagingData.empty(),
        val title: String = ""
    ) : HomeModel()

    data class PopularTvSeriesItem(
        val pagingData: PagingData<TvSeries> = PagingData.empty(),
        val title: String = ""
    ) : HomeModel()

    data class TopRatedMoviesItem(
        val pagingData: PagingData<Movie> = PagingData.empty(),
        val title: String = ""
    ) : HomeModel()

    data class TopRatedTvSeriesItem(
        val pagingData: PagingData<TvSeries> = PagingData.empty(),
        val title: String = ""
    ) : HomeModel()
}