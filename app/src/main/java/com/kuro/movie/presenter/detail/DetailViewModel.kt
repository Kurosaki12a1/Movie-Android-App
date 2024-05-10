package com.kuro.movie.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModelWithUiEvent
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.model.toMovie
import com.kuro.movie.domain.model.toTvSeries
import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.domain.usecase.detail.DetailUseCase
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.detail.event.DetailEvent
import com.kuro.movie.presenter.detail.event.DetailUIEvent
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Constants.DETAIL_DEFAULT_ID
import com.kuro.movie.util.Resource
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase,
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModelWithUiEvent<DetailUIEvent>() {
    private val _detailState = MutableLiveData(DetailState())
    val detailState: LiveData<DetailState>
        get() = _detailState

    private val _videos = MutableLiveData<Videos?>(null)
    val videos: LiveData<Videos?> = _videos

    init {
        initValue()
    }

    private fun initValue() {
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .movieId.let { id ->
                if (id == DETAIL_DEFAULT_ID) return@let
                _detailState.update {
                    it.copy(
                        movieId = id,
                        tvId = null
                    )
                }
                getMovieDetail(id)
            }

        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .tvId.let { id ->
                if (id == DETAIL_DEFAULT_ID) return@let
                _detailState.update {
                    it.copy(
                        movieId = null,
                        tvId = id
                    )
                }
                getTvDetail(id)
            }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.IntentToImdbWebSite -> {
                addConsumableViewEvent(
                    DetailUIEvent.IntentToImdbWebSite(
                        addLanguageQueryToTMDBUrl(
                            event.url
                        )
                    )
                )
            }

            is DetailEvent.ClickToDirectorName -> {
                //TODO
                /* val action = NavigateFlow.PersonDetailFlow(event.directorId)
                 addConsumableViewEvent(DetailUIEvent.NavigateTo(action))
             */
            }


            is DetailEvent.ClickActorName -> {
                //TODO
                /*       val action = NavigateFlow.PersonDetailFlow(event.actorId)
                       addConsumableViewEvent(DetailUIEvent.NavigateTo(action))*/
            }

            is DetailEvent.ClickedAddWatchList -> {
                val doesAddWatchList = _detailState.value?.doesAddWatchList ?: false
                _detailState.update {
                    it.copy(doesAddWatchList = !it.doesAddWatchList)
                }
                addWatchList(
                    onAddTvSeries = {
                        localDatabaseUseCases.toggleTvSeriesForWatchListItemUseCase(
                            tvSeries = detailState.value?.tvDetail?.toTvSeries()
                                ?: return@addWatchList,
                            doesAddWatchList = doesAddWatchList
                        )
                    },
                    onAddMovie = {
                        localDatabaseUseCases.toggleMovieForWatchListUseCase(
                            movie = detailState.value?.movieDetail?.toMovie()
                                ?: return@addWatchList,
                            doesAddWatchList = doesAddWatchList
                        )
                    }
                )
            }

            is DetailEvent.ClickedAddFavoriteList -> {
                val doesAddFavoriteList = detailState.value?.doesAddFavorite ?: false
                _detailState.update {
                    it.copy(
                        doesAddFavorite = !doesAddFavoriteList
                    )
                }
                addFavoriteList(
                    onAddTvSeries = {
                        localDatabaseUseCases.toggleTvSeriesForFavoriteListUseCase(
                            tvSeries = detailState.value?.tvDetail?.toTvSeries()
                                ?: return@addFavoriteList,
                            doesAddFavoriteList = doesAddFavoriteList
                        )
                    },
                    onAddMovie = {
                        localDatabaseUseCases.toggleMovieForFavoriteListUseCase(
                            movie = detailState.value?.movieDetail?.toMovie()
                                ?: return@addFavoriteList,
                            doesAddFavoriteList = doesAddFavoriteList
                        )
                    }
                )
            }


            is DetailEvent.ClickRecommendationItemClick -> {
                event.tvSeries?.let {
                    val action = NavigateFlow.BottomSheetDetailFlow(
                        tvSeries = it,
                        movie = null
                    )
                    addConsumableViewEvent(DetailUIEvent.NavigateTo(action))
                }
                event.movie?.let {
                    val action = NavigateFlow.BottomSheetDetailFlow(
                        tvSeries = null,
                        movie = it
                    )
                    addConsumableViewEvent(DetailUIEvent.NavigateTo(action))
                }
            }
        }
    }

    private fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }
            val resource = async {
                detailUseCase.getMovieVideosUseCase(
                    movieId = movieId,
                    language = Constants.DEFAULT_LANGUAGE
                )
            }
            when (val results = resource.await()) {
                is Resource.Success -> {
                    _detailState.update { it.copy(isLoading = false) }
                    _videos.postValue(results.value)
                }

                is Resource.Failure -> {
                    _detailState.update { it.copy(isLoading = false) }
                    handleError(results.error)
                }

                is Resource.Loading -> {
                    _detailState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun getTvVideos(tvId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }
            val resource = async {
                detailUseCase.getTvVideosUseCase(
                    tvId = tvId,
                    language = Constants.DEFAULT_LANGUAGE
                )
            }
            when (val results = resource.await()) {
                is Resource.Success -> {
                    _detailState.update { it.copy(isLoading = false) }
                    _videos.postValue(results.value)
                }

                is Resource.Failure -> {
                    _detailState.update { it.copy(isLoading = false) }
                    handleError(results.error)
                }

                is Resource.Loading -> {
                    _detailState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun addWatchList(
        onAddTvSeries: suspend () -> Unit,
        onAddMovie: suspend () -> Unit
    ) {
        viewModelScope.launch {
            if (detailState.value?.isNotNullTvDetail() == true) {
                onAddTvSeries()
            } else {
                onAddMovie()
            }
        }
    }

    private fun addFavoriteList(
        onAddTvSeries: suspend () -> Unit,
        onAddMovie: suspend () -> Unit
    ) {
        viewModelScope.launch {
            if (detailState.value?.isNotNullTvDetail() == true) {
                onAddTvSeries()
            } else {
                onAddMovie()
            }
        }
    }

    private fun addLanguageQueryToTMDBUrl(url: String): String {
        return url.plus("?language=${Constants.DEFAULT_LANGUAGE}")
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }
            val resource = async {
                detailUseCase.movieDetailUseCase(
                    language = Constants.DEFAULT_LANGUAGE,
                    movieId = movieId
                )
            }
            when (val results = resource.await()) {
                is Resource.Success -> {
                    val movieDetail = results.value
                    _detailState.update {
                        it.copy(
                            movieDetail = movieDetail,
                            tvDetail = null,
                            tvId = null,
                            isLoading = false,
                            doesAddFavorite = movieDetail.isFavorite,
                            doesAddWatchList = movieDetail.isWatchList
                        )
                    }
                    getMovieRecommendations(movieId = movieId)
                    getMovieVideos(movieId = movieId)
                }

                is Resource.Failure -> {
                    _detailState.update { it.copy(isLoading = false) }
                    handleError(results.error)
                }

                is Resource.Loading -> {
                    _detailState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun getTvDetail(tvId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }
            val resource = async {
                detailUseCase.tvDetailUseCase(
                    language = Constants.DEFAULT_LANGUAGE,
                    tvId = tvId
                )
            }
            when (val results = resource.await()) {
                is Resource.Success -> {
                    val tvDetail = results.value
                    _detailState.update {
                        it.copy(
                            movieDetail = null,
                            tvDetail = tvDetail,
                            movieId = null,
                            isLoading = false,
                            doesAddFavorite = tvDetail.isFavorite,
                            doesAddWatchList = tvDetail.isWatchList
                        )
                    }
                    getTvRecommendations(tvId = tvId)
                    getTvVideos(tvId = tvId)

                }

                is Resource.Failure -> {
                    _detailState.update { it.copy(isLoading = false) }
                    handleError(results.error)
                }

                is Resource.Loading -> {
                    _detailState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun getMovieRecommendations(movieId: Int) {
        viewModelScope.launch {
            val movie = detailUseCase.getMovieRecommendationUseCase(
                movieId = movieId, language = Constants.DEFAULT_LANGUAGE
            )
            _detailState.update { it.copy(movieRecommendation = movie) }
        }
    }

    private fun getTvRecommendations(tvId: Int) {
        viewModelScope.launch {
            val tv = detailUseCase.getTvRecommendationUseCase(
                tvId = tvId, language = Constants.DEFAULT_LANGUAGE
            )
            _detailState.update { it.copy(tvRecommendation = tv) }
        }
    }
}