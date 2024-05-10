package com.kuro.movie.presenter.detail

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.TvDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.model.WatchProviderItem

sealed class DetailModel {
    data class Poster(val posterPath: String = "") : DetailModel()
    data class Info(
        val movieDetail: MovieDetail? = null,
        val tvDetail: TvDetail? = null
    ) : DetailModel()

    data class WatchProvider(
        val doesAddFavorite: Boolean = false,
        val doesAddWatchList: Boolean = false,
        val watchProviders: WatchProviderItem? = null,
    ) : DetailModel()

    data class Actor(
        val movieDetail: MovieDetail? = null,
        val tvDetail: TvDetail? = null,
    ) : DetailModel()

    data class VideosTab(
        val movieRecommendation: List<Movie>? = null,
        val tvRecommendation: List<TvSeries>? = null,
        val videos: Videos? = null,
    ) : DetailModel()
}