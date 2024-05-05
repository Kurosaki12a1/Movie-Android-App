package com.kuro.movie.presenter.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuro.movie.R
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.util.postUpdate
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    private val localDatabaseUseCase: LocalDatabaseUseCases
) : BaseViewModel() {
    private val mutableState = MutableLiveData(MyLibraryState())
    val state: LiveData<MyLibraryState>
        get() = mutableState

    fun fetchData() {
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }
            val favoriteMovies = localDatabaseUseCase.getFavoriteMoviesUseCase()
            val favoriteTvSeries = localDatabaseUseCase.getFavoriteTvSeriesUseCase()
            val watchListMovies = localDatabaseUseCase.getMoviesInWatchListUseCase()
            val watchListTvSeries = localDatabaseUseCase.getTvSeriesInWatchListUseCase()
            when (mutableState.value?.chipType) {
                null, ChipType.MOVIE -> {
                    if (mutableState.value?.selectedTab?.isFavoriteList() == true) {
                        updateListMovieAndLoading(movieList = favoriteMovies)
                    } else {
                        updateListMovieAndLoading(movieList = watchListMovies)
                    }
                }

                ChipType.TV_SERIES -> {
                    if (mutableState.value?.selectedTab?.isFavoriteList() == true) {
                        updateListTvSeriesAndLoading(tvSeriesList = favoriteTvSeries)
                    } else {
                        updateListTvSeriesAndLoading(tvSeriesList = watchListTvSeries)
                    }
                }
            }
        }
    }

    fun onEvent(event: MyLibraryEvent) {
        when (event) {
            is MyLibraryEvent.SelectedTab -> {
                mutableState.update { it.copy(selectedTab = event.listTab) }
            }

            is MyLibraryEvent.UpdateListType -> {
                mutableState.update { it.copy(chipType = event.chipType) }
            }
        }
    }

    private fun updateListMovieAndLoading(movieList: List<Movie>) {
        mutableState.postUpdate {
            it.copy(
                movieList = movieList,
                isLoading = false,
                hasAnyMovieInList = movieList.isNotEmpty(),
                errorMessage = R.string.not_movies_in_your_list
            )
        }
    }

    private fun updateListTvSeriesAndLoading(tvSeriesList: List<TvSeries>) {
        return mutableState.postUpdate {
            it.copy(
                tvSeriesList = tvSeriesList,
                isLoading = false,
                hasAnyMovieInList = tvSeriesList.isNotEmpty(),
                errorMessage = R.string.not_tv_in_your_list
            )
        }
    }
}