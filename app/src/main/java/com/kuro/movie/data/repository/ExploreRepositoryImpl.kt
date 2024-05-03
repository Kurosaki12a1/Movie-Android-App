package com.kuro.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.data.paging_source.MultiSearchPagingSource
import com.kuro.movie.domain.model.FilterBottomState
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.repository.ExploreRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreMovieRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreMultiSearchRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreTvRemoteRepository
import com.kuro.movie.extension.toDiscoveryQueryString
import com.kuro.movie.extension.toSeparateWithComma
import com.kuro.movie.util.Constants
import com.kuro.movie.util.getPagingMovies
import com.kuro.movie.util.getPagingTvSeries
import io.reactivex.Observable
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreMovieRemote: ExploreMovieRemoteRepository,
    private val exploreTvRemote: ExploreTvRemoteRepository,
    private val exploreMultiSearchRemote: ExploreMultiSearchRemoteRepository
) : ExploreRepository {

    override fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<Movie>> {
        return getPagingMovies { page ->
            exploreMovieRemote.discoverMovie(
                page = page,
                language = language,
                genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                    filterBottomState.categoryState
                ),
            )
        }
    }

    override fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            exploreTvRemote.discoverTv(
                page = page,
                language = language,
                genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                    filterBottomState.categoryState
                ),
            )
        }
    }

    override fun multiSearch(
        query: String,
        language: String
    ): Observable<PagingData<MultiSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MultiSearchPagingSource(
                    fetchMultiSearch = { page ->
                        exploreMultiSearchRemote.discoverMovie(
                            query = query,
                            language = language,
                            page = page
                        ).results
                    }
                )
            }
        ).observable
    }
}