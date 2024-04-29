package com.kuro.movie.data.paging_source

import com.kuro.movie.core.BasePagingSource
import com.kuro.movie.data.mapper.toTvSeries
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.response.TvSeriesResponse
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val fetchTvSeries: suspend (page: Int) -> List<TvSeriesResponse>
) : BasePagingSource<TvSeries>() {
    override suspend fun fetchData(page: Int): List<TvSeries> {
        return fetchTvSeries(page).map { it.toTvSeries() }
    }
}

