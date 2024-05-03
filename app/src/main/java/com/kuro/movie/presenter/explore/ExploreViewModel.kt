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
import com.kuro.movie.util.asLiveData
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

    private val _networkState = MutableLiveData(ConnectivityObserver.Status.UNAVAILABLE)
    val networkState: LiveData<ConnectivityObserver.Status>
        get() = _networkState

    private val _filterBottomSheetState = MutableLiveData(FilterBottomState())
    val filterBottomState: MutableLiveData<FilterBottomState>
        get() = _filterBottomSheetState

    private var movieGenre = emptyList<Genre>()
    private var tvGenreList = emptyList<Genre>()

    private var observeQuery: Disposable? = null

    init {
        initValue()
    }

    private fun initValue() {
        viewModelScope.launch {
            collectNetworkState()
            getMovieGenreList()
            getTvGenreList()
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

    private fun getMovieGenreList() {
        viewModelScope.launch {
            movieGenre = genreRepository.getMovieGenreList().genres
        }
    }

    fun isNetworkAvailable(): Boolean {
        return networkState.value?.isAvailable() == true
    }

    fun multiSearch(query: String): LiveData<PagingData<MultiSearch>> {
        return if (query.isNotEmpty()) {
            exploreUseCases.multiSearchUseCase(query, Constants.DEFAULT_LANGUAGE)
                .cachedIn(viewModelScope).asLiveData()
        } else {
            MutableLiveData(PagingData.empty())
        }
    }

    suspend fun discoverMovie(): LiveData<PagingData<Movie>> {
        return exploreUseCases.discoverMovieUseCase(
            language = Constants.DEFAULT_LANGUAGE,
            filterBottomState = filterBottomState.value!!
        ).cachedIn(viewModelScope).asLiveData()
    }

    suspend fun discoverTv(): LiveData<PagingData<TvSeries>> {
        return exploreUseCases.discoverTvUseCase(
            language = Constants.DEFAULT_LANGUAGE,
            filterBottomState = filterBottomState.value!!
        ).cachedIn(viewModelScope).asLiveData()
    }

    fun onEventExploreFragment(event: ExploreFragmentEvent) {
        when (event) {
            is ExploreFragmentEvent.MultiSearch -> {
                // if current query search same with previous query, do nothing
                if (event.query == _query.value) return
                _query.postValue(event.query)
                if (event.query.isNotEmpty()) {
                    _filterBottomSheetState.value?.copy(
                        categoryState = Category.SEARCH
                    )?.let {
                        _filterBottomSheetState.postValue(
                            it
                        )
                    }
                } else {
                    _filterBottomSheetState.value?.copy(
                        categoryState = Category.MOVIE
                    )?.let {
                        _filterBottomSheetState.postValue(
                            it
                        )
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
                _filterBottomSheetState.value?.copy(
                    categoryState = event.checkedCategory
                )?.let {
                    _filterBottomSheetState.postValue(
                        it
                    )
                }
                getGenreListByCategoriesState()
            }

            is ExploreBottomSheetEvent.UpdateGenreList -> {
                resetSelectedGenreIdsState()
                _filterBottomSheetState.value?.copy(
                    checkedGenreIdsState = event.checkedList
                )?.let {
                    _filterBottomSheetState.postValue(
                        it
                    )
                }
            }

            is ExploreBottomSheetEvent.UpdateSort -> {
                _filterBottomSheetState.value?.copy(
                    checkedSortState = event.checkedSort
                )?.let {
                    _filterBottomSheetState.postValue(
                        it
                    )
                }
            }

            is ExploreBottomSheetEvent.ResetFilterBottomState -> {
                _filterBottomSheetState.postValue(FilterBottomState())
            }

            is ExploreBottomSheetEvent.Apply -> {
                // TODO POP BAC STACK EXPLORE BOTTOM SHEET
            }
        }
    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.value?.copy(
            checkedGenreIdsState = emptyList()
        )?.let {
            _filterBottomSheetState.postValue(
                it
            )
        }
    }

    private fun getGenreListByCategoriesState() {
        viewModelScope.launch {
            try {
                if (_filterBottomSheetState.value?.categoryState?.isTv() == true) {
                    _genreList.postValue(tvGenreList)
                } else {
                    _genreList.postValue(movieGenre)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun getTvGenreList() {
        viewModelScope.launch {
            tvGenreList = genreRepository.getTvGenreList().genres
        }
    }
}