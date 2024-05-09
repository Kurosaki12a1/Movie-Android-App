package com.kuro.movie.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase
) : BaseViewModel() {
    private val _nowPlayingMovie = MutableLiveData<PagingData<Movie>>()
    val nowPlayingMovie: LiveData<PagingData<Movie>>
        get() = _nowPlayingMovie

    private val _popularMovie = MutableLiveData<PagingData<Movie>>()
    val popularMovie: LiveData<PagingData<Movie>>
        get() = _popularMovie

    private val _topRatedMovie = MutableLiveData<PagingData<Movie>>()
    val topRatedMovie: LiveData<PagingData<Movie>>
        get() = _topRatedMovie

    private val _popularTvSeries = MutableLiveData<PagingData<TvSeries>>()
    val popularTvSeries: LiveData<PagingData<TvSeries>>
        get() = _popularTvSeries

    private val _topRatedTvSeries = MutableLiveData<PagingData<TvSeries>>()
    val topRatedTvSeries: LiveData<PagingData<TvSeries>>
        get() = _topRatedTvSeries

    private var nowPlayingDisposable: Disposable? = null
    private var popularMovieDisposable: Disposable? = null
    private var topRatedMovieDisposable: Disposable? = null
    private var popularTvSeriesDisposable: Disposable? = null
    private var topRatedTvSeriesDisposable: Disposable? = null

    init {
        fetchData()
    }

    fun fetchData() {
        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getPopularTvSeries()
        getTopRatedTvSeries()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            nowPlayingDisposable?.dispose()

            nowPlayingDisposable = getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _nowPlayingMovie.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            popularMovieDisposable?.dispose()

            popularMovieDisposable = getPopularMoviesUseCase().cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    _popularMovie.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            topRatedMovieDisposable?.dispose()

            topRatedMovieDisposable = getTopRatedMoviesUseCase().cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    _topRatedMovie.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }

    private fun getPopularTvSeries() {
        viewModelScope.launch {
            popularTvSeriesDisposable?.dispose()

            popularTvSeriesDisposable = getPopularTvSeriesUseCase().cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    _popularTvSeries.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }

    private fun getTopRatedTvSeries() {
        viewModelScope.launch {
            topRatedTvSeriesDisposable?.dispose()

            topRatedTvSeriesDisposable = getTopRatedTvSeriesUseCase().cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    _topRatedTvSeries.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }
}