@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kuro.movie.presenter.see_all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetPopularTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTopRatedTvSeriesUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.postUpdate
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData(SeeAllState())
    val state: LiveData<SeeAllState>
        get() = _state

    private val _movieData = MutableLiveData<PagingData<Movie>?>(null)
    val movieData: LiveData<PagingData<Movie>?>
        get() = _movieData

    private val _tvSeriesData = MutableLiveData<PagingData<TvSeries>?>(null)
    val tvSeriesData: LiveData<PagingData<TvSeries>?>
        get() = _tvSeriesData

    private var dataDisposable: Disposable? = null

    init {
        SeeAllFragmentArgs.fromSavedStateHandle(savedStateHandle).title.let { title ->
            _state.update { it.copy(title = title) }
        }
        fetchData()
    }

    private fun fetchData() {
        SeeAllFragmentArgs.fromSavedStateHandle(savedStateHandle).homeCategory.let { category ->
            when (category) {
                Constants.NOW_PLAYING -> {
                    viewModelScope.launch {
                        dataDisposable?.dispose()
                        _state.postUpdate { it.copy(isLoading = true) }
                        dataDisposable = getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
                            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                            .subscribe({ data ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                _movieData.postValue(data)
                            }, { error ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                handleError(error)
                            }).also { addDisposable(it) }
                    }
                }

                Constants.POPULAR_MOVIE -> {
                    viewModelScope.launch {
                        dataDisposable?.dispose()
                        _state.postUpdate { it.copy(isLoading = true) }
                        dataDisposable = getPopularMoviesUseCase().cachedIn(viewModelScope)
                            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                            .subscribe({ data ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                _movieData.postValue(data)
                            }, { error ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                handleError(error)
                            }).also { addDisposable(it) }
                    }
                }

                Constants.POPULAR_TV_SERIES -> {
                    viewModelScope.launch {
                        dataDisposable?.dispose()
                        _state.postUpdate { it.copy(isLoading = true) }
                        dataDisposable = getPopularTvSeriesUseCase().cachedIn(viewModelScope)
                            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                            .subscribe({ data ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                _tvSeriesData.postValue(data)
                            }, { error ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                handleError(error)
                            }).also { addDisposable(it) }
                    }
                }

                Constants.TOP_RATED_MOVIE -> {
                    viewModelScope.launch {
                        dataDisposable?.dispose()
                        _state.postUpdate { it.copy(isLoading = true) }
                        dataDisposable = getTopRatedMoviesUseCase().cachedIn(viewModelScope)
                            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                            .subscribe({ data ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                _movieData.postValue(data)
                            }, { error ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                handleError(error)
                            }).also { addDisposable(it) }
                    }
                }

                Constants.TOP_RATED_TV_SERIES -> {
                    viewModelScope.launch {
                        dataDisposable?.dispose()
                        _state.postUpdate { it.copy(isLoading = true) }
                        dataDisposable = getTopRatedTvSeriesUseCase().cachedIn(viewModelScope)
                            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                            .subscribe({ data ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                _tvSeriesData.postValue(data)
                            }, { error ->
                                _state.postUpdate { it.copy(isLoading = false) }
                                handleError(error)
                            }).also { addDisposable(it) }
                    }
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }
}
