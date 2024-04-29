package com.kuro.movie.presenter.home

import com.kuro.movie.data.model.Genre

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: String? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList(),
)