package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Genre

data class FilterBottomState(
    val categoryState: Category = Category.MOVIE,
    val checkedGenreIdsState: List<Int> = emptyList(),
    val checkedSortState: Sort = Sort.Popularity,
    val genreList: List<Genre> = listOf()
)