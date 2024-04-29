package com.kuro.movie.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.kuro.movie.data.model.ApiResponse
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.data.paging_source.MoviesPagingSource
import com.kuro.movie.data.paging_source.TvPagingSource
import com.kuro.movie.domain.response.MovieResponse
import com.kuro.movie.domain.response.TvSeriesResponse
import io.reactivex.Observable

inline fun getPagingTvSeries(
    crossinline tvFetcher: suspend (page: Int) -> ApiResponse<TvSeriesResponse>
): Observable<PagingData<TvSeries>> {
    return Pager(
        config = PagingConfig(
            pageSize = Constants.ITEMS_PER_PAGE,
        ),
        pagingSourceFactory = { TvPagingSource(fetchTvSeries = { page -> tvFetcher(page).results }) }
    ).observable
}

inline fun getPagingMovies(
    crossinline movieFetcher: suspend (page: Int) -> ApiResponse<MovieResponse>
): Observable<PagingData<Movie>> {
    return Pager(
        config = PagingConfig(
            pageSize = Constants.ITEMS_PER_PAGE,
        ),
        pagingSourceFactory = { MoviesPagingSource(fetchMovie = { page -> movieFetcher(page).results }) }
    ).observable
}