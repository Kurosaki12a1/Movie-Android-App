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

    private val _moviePagingData = MutableLiveData<PagingData<Movie>>()
    val moviePagingData: LiveData<PagingData<Movie>>
        get() = _moviePagingData

    private val _tvSeriesPagingData = MutableLiveData<PagingData<TvSeries>>()
    val tvSeriesPagingData: LiveData<PagingData<TvSeries>>
        get() = _tvSeriesPagingData

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _moviePagingData.postValue(it)
                }, { error ->
                    handleError(error)
                })
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _moviePagingData.postValue(it)
                }, { error ->
                    handleError(error)
                })
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            getTopRatedMoviesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _moviePagingData.postValue(it)
                }, { error ->
                    handleError(error)
                })
        }
    }

    fun getPopularTvSeries() {
        viewModelScope.launch {
            getPopularTvSeriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _tvSeriesPagingData.postValue(it)
                }, { error ->
                    handleError(error)
                })
        }
    }

    fun getTopRatedTvSeries() {
        viewModelScope.launch {
            getTopRatedTvSeriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _tvSeriesPagingData.postValue(it)
                }, { error ->
                    handleError(error)
                })
        }
    }
}