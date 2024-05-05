package com.kuro.movie.presenter.detail_bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.util.postUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : BaseViewModel() {

    private val _state = MutableLiveData(DetailBottomSheetState())
    val state: LiveData<DetailBottomSheetState>
        get() = _state

    private val _uiEvent = MutableLiveData<DetailBottomUiEvent>()
    val uiEvent: LiveData<DetailBottomUiEvent> = _uiEvent

    init {
        combineUseCases()
        DetailBottomSheetArgs.fromSavedStateHandle(savedStateHandle).movie?.let { movie ->
            _state.postUpdate { it.copy(movie = movie) }
        }

        DetailBottomSheetArgs.fromSavedStateHandle(savedStateHandle).tvseries?.let { tvSeries ->
            _state.postUpdate { it.copy(tvSeries = tvSeries) }
        }
    }

    private fun combineUseCases() {
        viewModelScope.launch {
            val favoriteMovieIds = localDatabaseUseCases.getFavoriteMovieIdsUseCase()
            val movieWatchListIds = localDatabaseUseCases.getMovieWatchListItemIdsUseCase()
            val favoriteTvIds = localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase()
            val tvWatchListIds = localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
            val newState = DetailBottomSheetState(
                doesAddFavorite = _state.value?.movie?.let { movie ->
                    favoriteMovieIds.contains(movie.id)
                } ?: favoriteTvIds.contains(_state.value?.tvSeries?.id ?: 0),
                doesAddWatchList = _state.value?.movie?.let { movie ->
                    movieWatchListIds.contains(movie.id)
                } ?: tvWatchListIds.contains(_state.value?.tvSeries?.id ?: 0)
            )
            _state.postUpdate {
                it.copy(
                    doesAddFavorite = newState.doesAddFavorite,
                    doesAddWatchList = newState.doesAddWatchList
                )
            }
        }
    }

    private fun getMovie(): Movie? = _state.value?.movie

    private fun getTvSeries(): TvSeries? = _state.value?.tvSeries


    fun onEvent(event: DetailBottomSheetEvent) {
        when (event) {
            is DetailBottomSheetEvent.Close -> {
                emitUiEvent(DetailBottomUiEvent.PopBackStack)
            }

            is DetailBottomSheetEvent.NavigateToDetailFragment -> {
                // TODO
                //     navigateToDetailFragment(movieId = getMovie()?.id, tvId = getTvSeries()?.id)
            }

            is DetailBottomSheetEvent.Share -> {

            }

            is DetailBottomSheetEvent.ClickedAddFavoriteList -> {
                getMovie()?.let { movie ->
                    toggleMovieForFavoriteList(movie = movie)
                }
                getTvSeries()?.let { tvSeries ->
                    toggleTvSeriesForFavoriteList(tvSeries = tvSeries)
                }

            }

            is DetailBottomSheetEvent.ClickedAddWatchList -> {
                getMovie()?.let { movie ->
                    toggleMovieForWatchList(movie = movie)
                }
                getTvSeries()?.let { tvSeries ->
                    toggleTvSeriesForWatchList(tvSeries = tvSeries)
                }
            }
        }
    }

    private fun toggleMovieForFavoriteList(movie: Movie) {
        viewModelScope.launch {
            // Toggle the movie's favorite status and wait for completion
            localDatabaseUseCases.toggleMovieForFavoriteListUseCase(
                movie = movie, doesAddFavoriteList = state.value?.doesAddFavorite ?: false
            )

            // Once toggle is done, execute additional functionality, like adding to favorites.
            val favoriteMovieIds = localDatabaseUseCases.getFavoriteMovieIdsUseCase()
            _state.postUpdate { it.copy(doesAddFavorite = favoriteMovieIds.contains(it.movie?.id)) }
        }
    }

    private fun toggleMovieForWatchList(movie: Movie) {
        viewModelScope.launch {
            // Toggle the movie's watch list status and wait for completion
            localDatabaseUseCases.toggleMovieForWatchListUseCase(
                movie = movie, doesAddWatchList = state.value?.doesAddWatchList ?: false
            )

            // Once toggle is done, execute additional functionality, like adding to watch list.
            val movieWatchListIds = localDatabaseUseCases.getMovieWatchListItemIdsUseCase()
            _state.postUpdate { it.copy(doesAddWatchList = movieWatchListIds.contains(it.movie?.id)) }
        }
    }

    private fun toggleTvSeriesForFavoriteList(tvSeries: TvSeries) {
        viewModelScope.launch {
            // Toggle the movie's watch list status and wait for completion
            localDatabaseUseCases.toggleTvSeriesForFavoriteListUseCase(
                tvSeries = tvSeries,
                doesAddFavoriteList = state.value?.doesAddFavorite ?: false
            )

            // Once toggle is done, execute additional functionality, like adding to favorite list.
            val favoriteTvIds = localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase()
            _state.postUpdate { it.copy(doesAddFavorite = favoriteTvIds.contains(it.tvSeries?.id)) }
        }
    }

    private fun toggleTvSeriesForWatchList(tvSeries: TvSeries) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleTvSeriesForWatchListItemUseCase(
                tvSeries = tvSeries,
                doesAddWatchList = state.value?.doesAddWatchList ?: false
            )

            val tvWatchListIds = localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
            _state.postUpdate { it.copy(doesAddWatchList = tvWatchListIds.contains(it.tvSeries?.id)) }
        }
    }

    private fun navigateToDetailFragment(movieId: Int? = null, tvId: Int? = null) {
        movieId?.let {
            emitUiEvent(
                DetailBottomUiEvent.NavigateTo(
                    NavigateFlow.DetailFlow(movieId = movieId)
                )
            )
        }
        tvId?.let {
            emitUiEvent(
                DetailBottomUiEvent.NavigateTo(
                    NavigateFlow.DetailFlow(tvSeriesId = tvId)
                )
            )
        }
    }

    private fun emitUiEvent(uiEvent: DetailBottomUiEvent) {
        _uiEvent.postValue(uiEvent)
    }
}