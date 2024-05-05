package com.kuro.movie.domain.model

import com.kuro.movie.data.model.Movie

data class UpComingMovie(
    val movie: Movie,
    val isAddedToRemind: Boolean
)