package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries

sealed interface MultiSearch {
    data class MovieItem(val movie: Movie) : MultiSearch
    data class TvSeriesItem(val tvSeries: TvSeries) : MultiSearch
    data class PersonItem(val person: PersonSearch) : MultiSearch
}