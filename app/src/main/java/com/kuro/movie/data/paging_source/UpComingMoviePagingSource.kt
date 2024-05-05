package com.kuro.movie.data.paging_source

import androidx.paging.PagingState
import com.kuro.movie.core.BasePagingSource
import com.kuro.movie.data.mapper.toUpComingMovie
import com.kuro.movie.domain.model.UpComingMovie
import com.kuro.movie.domain.response.MovieResponse
import javax.inject.Inject

class UpComingMoviePagingSource @Inject constructor(
    private val fetchUpcomingMovie: suspend (page: Int) -> List<MovieResponse>
) : BasePagingSource<UpComingMovie>() {
    override fun getRefreshKey(state: PagingState<Int, UpComingMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun fetchData(page: Int): List<UpComingMovie> {
        return fetchUpcomingMovie(page).map { it.toUpComingMovie() }
    }
}