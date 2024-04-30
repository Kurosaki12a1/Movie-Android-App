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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            _state.postValue(DetailBottomSheetState(movie = movie))
        }

        DetailBottomSheetArgs.fromSavedStateHandle(savedStateHandle).tvseries?.let { tvSeries ->
            _state.postValue(DetailBottomSheetState(tvSeries = tvSeries))
        }
    }

    private fun combineUseCases() {
        Flowable.combineLatest(
            localDatabaseUseCases.getFavoriteMovieIdsUseCase(),
            localDatabaseUseCases.getMovieWatchListItemIdsUseCase(),
            localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase(),
            localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
        ) { favoriteMovieIds, movieWatchListIds, favoriteTvIds, tvWatchListIds ->
            // Create a new instance of DetailBottomSheetState
            DetailBottomSheetState(
                doesAddFavorite = _state.value?.movie?.let { movie ->
                    favoriteMovieIds.contains(movie.id)
                } ?: favoriteTvIds.contains(_state.value?.tvSeries?.id ?: 0),
                doesAddWatchList = _state.value?.movie?.let { movie ->
                    movieWatchListIds.contains(movie.id)
                } ?: tvWatchListIds.contains(_state.value?.tvSeries?.id ?: 0)
            )
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newState -> _state.postValue(newState) }, { error -> handleError(error) })
            .let { addDisposable(it) }
    }

    private fun getMovie(): Movie? {
        return state.value?.movie
    }

    private fun getTvSeries(): TvSeries? {
        return state.value?.tvSeries
    }


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
            localDatabaseUseCases.toggleMovieForFavoriteListUseCase(
                movie = movie, doesAddFavoriteList = state.value?.doesAddFavorite == true
            )
        }
    }

    private fun toggleMovieForWatchList(movie: Movie) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleMovieForWatchListUseCase(
                movie = movie, doesAddWatchList = state.value?.doesAddWatchList == true
            )
        }
    }

    private fun toggleTvSeriesForFavoriteList(tvSeries: TvSeries) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleTvSeriesForFavoriteListUseCase(
                tvSeries = tvSeries,
                doesAddFavoriteList = state.value?.doesAddFavorite == true
            )
        }
    }

    private fun toggleTvSeriesForWatchList(tvSeries: TvSeries) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleTvSeriesForWatchListItemUseCase(
                tvSeries = tvSeries,
                doesAddWatchList = state.value?.doesAddWatchList == true
            )
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