package com.kuro.movie.presenter.up_coming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.alarm_manager.UpComingAlarmScheduler
import com.kuro.movie.domain.model.UpComingAlarmItem
import com.kuro.movie.domain.model.UpComingMovie
import com.kuro.movie.domain.repository.UpComingRepository
import com.kuro.movie.domain.usecase.up_coming.GetUpcomingMovieUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor(
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase,
    private val upcomingRepository: UpComingRepository,
    private val upComingAlarmScheduler: UpComingAlarmScheduler
) : BaseViewModel() {

    private val mutableState = MutableLiveData(UpComingState())
    val state: LiveData<UpComingState>
        get() = mutableState

    private val _upcomingMovies = MutableLiveData<PagingData<UpComingMovie>>()
    val upcomingMovies: LiveData<PagingData<UpComingMovie>>
        get() = _upcomingMovies

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getUpcomingMovieUseCase(Constants.DEFAULT_LANGUAGE, this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _upcomingMovies.postValue(it)
                }, {
                    handleError(it)
                }).let { addDisposable(it) }
        }
    }

    fun onEvent(event: UpComingEvent) {
        when (event) {
            UpComingEvent.Loading -> {
                mutableState.update { it.copy(isLoading = true) }
            }

            UpComingEvent.NotLoading -> {
                mutableState.update { it.copy(isLoading = false) }
            }

            is UpComingEvent.Error -> {
                mutableState.update { it.copy(error = event.message) }
            }

            is UpComingEvent.OnClickRemindMe -> {
                viewModelScope.launch {
                    toggleRemindMe(
                        isAddedToRemind = event.upcomingMovie.isAddedToRemind,
                        upcomingMovie = event.upcomingMovie
                    )
                }
            }
        }
    }

    private fun toggleRemindMe(
        isAddedToRemind: Boolean,
        upcomingMovie: UpComingMovie
    ) {
        val upcomingAlarmItem = UpComingAlarmItem(
            movieId = upcomingMovie.movie.id,
            movieTitle = upcomingMovie.movie.title,
            movieReleaseDate = upcomingMovie.movie.fullReleaseDate ?: ""
        )

        viewModelScope.launch {
            if (isAddedToRemind) {
                upcomingRepository.deleteUpcomingRemind(upcomingMovie)
                upComingAlarmScheduler.cancelAlarm(upcomingAlarmItem)
            } else {
                upcomingRepository.insertUpcomingRemind(upcomingMovie)
                upComingAlarmScheduler.scheduleAlarm(upcomingAlarmItem)
            }
        }
    }
}