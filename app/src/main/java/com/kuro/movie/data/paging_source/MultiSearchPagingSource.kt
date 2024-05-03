package com.kuro.movie.data.paging_source

import androidx.paging.PagingState
import com.kuro.movie.core.BasePagingSource
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.response.SearchResponse
import com.kuro.movie.domain.response.multiSearch

class MultiSearchPagingSource(
    private val fetchMultiSearch: suspend (Int) -> List<SearchResponse>
) : BasePagingSource<MultiSearch>() {
    override fun getRefreshKey(state: PagingState<Int, MultiSearch>): Int? {
        return state.anchorPosition
    }

    override suspend fun fetchData(page: Int): List<MultiSearch> {
        return fetchMultiSearch(page).map { it.multiSearch()!! }
    }
}