package com.kuro.movie.presenter.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.data.model.Genre
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.Category
import com.kuro.movie.domain.model.FilterBottomState
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.model.isTv
import com.kuro.movie.domain.repository.ConnectivityObserver
import com.kuro.movie.domain.repository.GenreRepository
import com.kuro.movie.domain.repository.isAvailable
import com.kuro.movie.domain.usecase.ExploreUseCases
import com.kuro.movie.util.Constants
import com.kuro.movie.util.postUpdate
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCases: ExploreUseCases,
    private val observeNetwork: ConnectivityObserver,
    private val genreRepository: GenreRepository
) : BaseViewModel() {

    private val _query = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    private val _genreList = MutableLiveData<List<Genre>>(emptyList())
    val genreList: LiveData<List<Genre>>
        get() = _genreList

    private val _discoverMovie = MutableLiveData<PagingData<Movie>>()
    val discoverMovie: LiveData<PagingData<Movie>>
        get() = _discoverMovie

    private val _discoverTv = MutableLiveData<PagingData<TvSeries>>()
    val discoverTv: LiveData<PagingData<TvSeries>>
        get() = _discoverTv

    private val _multiSearch = MutableLiveData<PagingData<MultiSearch>>()
    val multiSearch: LiveData<PagingData<MultiSearch>>
        get() = _multiSearch

    private val _networkState = MutableLiveData(ConnectivityObserver.Status.UNAVAILABLE)
    val networkState: LiveData<ConnectivityObserver.Status>
        get() = _networkState

    private val _filterBottomSheetState = MutableLiveData(FilterBottomState())
    val filterBottomSheetState: MutableLiveData<FilterBottomState>
        get() = _filterBottomSheetState

    private var movieGenreList = emptyList<Genre>()
    private var tvGenreList = emptyList<Genre>()

    private var discoverMovieDisposable: Disposable? = null
    private var discoverTvDisposable: Disposable? = null
    private var multiSearchDisposable: Disposable? = null

    private var previousCategory = Category.MOVIE

    init {
        initValue()
        observerDiscoverMovie()
        observerDiscoverTv()
    }

    private fun initValue() {
        viewModelScope.launch {
            collectNetworkState()
            getMovieGenreList()
            getTvGenreList()
            getGenreListByCategoriesState()
        }
    }

    private fun collectNetworkState() {
        observeNetwork.observe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _networkState.postValue(it)
            }, {
                handleError(it)
            }).also { addDisposable(it) }
    }

    private suspend fun getMovieGenreList() {
        movieGenreList = genreRepository.getMovieGenreList().genres
    }

    fun isNetworkAvailable(): Boolean {
        return networkState.value?.isAvailable() == true
    }

    fun multiSearch(query: String) {
        multiSearchDisposable?.dispose()
        multiSearchDisposable =
            exploreUseCases.multiSearchUseCase(query, Constants.DEFAULT_LANGUAGE)
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (query.isNotEmpty()) {
                        _multiSearch.postValue(it)
                    } else {
                        _multiSearch.postValue(PagingData.empty())
                    }
                }, {
                    handleError(it)
                }).also { addDisposable(it) }
    }

    private fun observerDiscoverMovie() {
        viewModelScope.launch {
            discoverMovieDisposable?.dispose()
            discoverMovieDisposable = exploreUseCases.discoverMovieUseCase(
                language = Constants.DEFAULT_LANGUAGE,
                filterBottomState = _filterBottomSheetState.value!!
            ).cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _discoverMovie.postValue(it)
                }, {
                    handleError(it)
                }).also { addDisposable(it) }
        }
    }

    private fun observerDiscoverTv() {
        viewModelScope.launch {
            discoverTvDisposable?.dispose()

            discoverTvDisposable = exploreUseCases.discoverTvUseCase(
                language = Constants.DEFAULT_LANGUAGE,
                filterBottomState = _filterBottomSheetState.value!!
            ).cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _discoverTv.postValue(it)
                }, {
                    handleError(it)
                }).also { addDisposable(it) }
        }
    }

    fun onEventExploreFragment(event: ExploreFragmentEvent) {
        when (event) {
            is ExploreFragmentEvent.MultiSearch -> {
                // if current query search same with previous query, do nothing
                if (event.query == _query.value) return
                if (_filterBottomSheetState.value?.categoryState != Category.SEARCH) {
                    // Get previous category selected before go search
                    previousCategory =
                        _filterBottomSheetState.value?.categoryState ?: Category.MOVIE
                }
                _query.postValue(event.query)
                if (event.query.isNotEmpty()) {
                    _filterBottomSheetState.postUpdate {
                        it.copy(categoryState = Category.SEARCH)
                    }
                } else {
                    _filterBottomSheetState.postUpdate {
                        // If search query is blank then return to previous category
                        it.copy(categoryState = previousCategory)
                    }
                }
            }

            is ExploreFragmentEvent.RemoveQuery -> {
                _query.postValue("")
            }
        }
    }

    fun onEventBottomSheet(event: ExploreBottomSheetEvent) {
        when (event) {
            is ExploreBottomSheetEvent.UpdateCategory -> {
                // When user switch category, reset selected genre list
                resetSelectedGenreIdsState()
                _filterBottomSheetState.update { currentState ->
                    currentState.copy(categoryState = event.checkedCategory)
                }
                getGenreListByCategoriesState()
            }

            is ExploreBottomSheetEvent.UpdateGenreList -> {
                _filterBottomSheetState.update { currentState ->
                    currentState.copy(checkedGenreIdsState = event.checkedList)
                }
                fetchData()
            }

            is ExploreBottomSheetEvent.UpdateSort -> {
                _filterBottomSheetState.postUpdate {
                    it.copy(checkedSortState = event.checkedSort)
                }
                fetchData()
            }

            is ExploreBottomSheetEvent.OpenFilter -> {
                _filterBottomSheetState.postUpdate {
                    it.copy(isExpanded = true)
                }
            }

            is ExploreBottomSheetEvent.ResetFilterBottomState -> {
                _filterBottomSheetState.postValue(FilterBottomState(isExpanded = true))
                fetchData()
            }

            is ExploreBottomSheetEvent.Apply -> {
                _filterBottomSheetState.value?.copy(
                    isExpanded = false
                )?.let { _filterBottomSheetState.postValue(it) }
            }
        }
    }

    private fun fetchData() {
        when (filterBottomSheetState.value?.categoryState) {
            null -> {
                observerDiscoverMovie()
            }

            Category.MOVIE -> {
                observerDiscoverMovie()
            }

            Category.TV -> {
                observerDiscoverTv()
            }

            else -> {
                // TODO
                // Do nothing
            }
        }
    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.update {
            it.copy(checkedGenreIdsState = emptyList())
        }
    }

    private fun getGenreListByCategoriesState() {
        if (_filterBottomSheetState.value?.categoryState?.isTv() == true) {
            _genreList.postValue(tvGenreList)
        } else {
            _genreList.postValue(movieGenreList)
        }
    }

    private suspend fun getTvGenreList() {
        tvGenreList = genreRepository.getTvGenreList().genres
    }
}