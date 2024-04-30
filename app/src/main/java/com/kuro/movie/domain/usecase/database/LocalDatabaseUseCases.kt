package com.kuro.movie.domain.usecase.database

import com.kuro.movie.domain.usecase.movie.GetFavoriteMovieIdsUseCase
import com.kuro.movie.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieWatchListItemIdsUseCase
import com.kuro.movie.domain.usecase.movie.GetMoviesInWatchListUseCase
import com.kuro.movie.domain.usecase.movie.ToggleMovieForFavoriteListUseCase
import com.kuro.movie.domain.usecase.movie.ToggleMovieForWatchListUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesIdsUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesInWatchListUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesWatchListItemIdsUseCase
import com.kuro.movie.domain.usecase.tvseries.ToggleTvSeriesForFavoriteListUseCase
import com.kuro.movie.domain.usecase.tvseries.ToggleTvSeriesForWatchListItemUseCase

data class LocalDatabaseUseCases(
    val clearAllDatabaseUseCase: ClearAllDatabaseUseCase,
    val toggleMovieForFavoriteListUseCase: ToggleMovieForFavoriteListUseCase,
    val toggleMovieForWatchListUseCase: ToggleMovieForWatchListUseCase,
    val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    val getMovieWatchListItemIdsUseCase: GetMovieWatchListItemIdsUseCase,
    val toggleTvSeriesForFavoriteListUseCase: ToggleTvSeriesForFavoriteListUseCase,
    val toggleTvSeriesForWatchListItemUseCase: ToggleTvSeriesForWatchListItemUseCase,
    val getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
    val getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase,
    val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    val getFavoriteTvSeriesUseCase: GetFavoriteTvSeriesUseCase,
    val getMoviesInWatchListUseCase: GetMoviesInWatchListUseCase,
    val getTvSeriesInWatchListUseCase: GetTvSeriesInWatchListUseCase
)