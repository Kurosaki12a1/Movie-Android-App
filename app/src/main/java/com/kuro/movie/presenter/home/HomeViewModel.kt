package com.kuro.movie.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetPopularTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTopRatedTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase
) : BaseViewModel() {
    private val _homeState = MutableLiveData(HomeState())
    val homeState: LiveData<HomeState>
        get() = _homeState

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

    fun clickSeeAllText(text: String) {
        val newState = _homeState.value?.copy(
            isShowsSeeAllPage = true,
            seeAllPageToolBarText = text
        )
        _homeState.postValue(newState)
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

            nowPlayingDisposable = getNowPlayingMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

            popularMovieDisposable = getPopularMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

            topRatedMovieDisposable = getTopRatedMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

            popularTvSeriesDisposable = getPopularTvSeriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

            topRatedTvSeriesDisposable = getTopRatedTvSeriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _topRatedTvSeries.postValue(it)
                }, { error ->
                    handleError(error)
                }).also { addDisposable(it) }
        }
    }
}