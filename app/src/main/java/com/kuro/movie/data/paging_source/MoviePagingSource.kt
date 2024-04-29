package com.kuro.movie.data.paging_source

import com.kuro.movie.core.BasePagingSource
import com.kuro.movie.data.mapper.toMovie
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.response.MovieResponse
import io.reactivex.Single
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val fetchMovie: suspend (page: Int) -> List<MovieResponse>
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> {
        return fetchMovie(page).map { it.toMovie() }
    }
}

