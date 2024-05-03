package com.kuro.movie.domain.usecase

import com.kuro.movie.domain.usecase.movie.DiscoverMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieGenreListUseCase
import com.kuro.movie.domain.usecase.tvseries.DiscoverTvUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvGenreListUseCase

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreListUseCase,
    val movieGenreListUseCase: GetMovieGenreListUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase,
    val multiSearchUseCase: MultiSearchUseCase
)