package com.kuro.movie.presenter.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.ChangePasswordUseCase
import com.kuro.movie.domain.usecase.auth.DeleteAccountUseCase
import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.domain.usecase.firebase.GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.firebase.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.firebase.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.settings.SettingsUseCase
import com.kuro.movie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val settingsUseCase: SettingsUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase: GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase,
    private val getMovieWatchListFromLocalDatabaseThenUpdateToFirebase: GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase,
    private val getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase: GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase,
    private val getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase: GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
) : BaseViewModel() {

    private val _mutableState = MutableLiveData(SettingsUiState())
    val state: LiveData<SettingsUiState>
        get() = _mutableState

    init {
        initState()
    }

    private fun initState() {
        val isUserSignIn = auth.currentUser != null
        _mutableState.postValue(
            _mutableState.value?.copy(
                isSignedIn = isUserSignIn,
                userProfile = auth.currentUser
            )
        )
    }

    fun updateUIMode(uiMode: Int) {
        settingsUseCase.updateUIModeUseCase(uiMode)
        _mutableState.postValue(_mutableState.value?.copy(uiMode = uiMode))
    }

    fun logOut() {
        viewModelScope.launch {
            _mutableState.postValue(_mutableState.value?.copy(isLoading = true))
            getMovieFavoriteFromLocalDatabaseThenUpdateFirebase()
            getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase()
            delay(2000)
            signOut()
            withContext(Dispatchers.IO) {
                localDatabaseUseCases.clearAllDatabaseUseCase()
            }
            _mutableState.postValue(_mutableState.value?.copy(isLoading = false))
        }
    }

    private fun signOut() {
        auth.signOut()
        _mutableState.postValue(_mutableState.value?.copy(isSignedIn = false))
        sendMessage("Successfully log out")
    }

    private fun getMovieFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            val result = getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase()
            if (result is Resource.Failure) {
                handleError(Exception("Something went wrong!"))
            }
        }
    }

    private fun getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            val result = getMovieWatchListFromLocalDatabaseThenUpdateToFirebase()
            if (result is Resource.Failure) {
                handleError(Exception("Something went wrong!"))
            }
        }
    }

    private fun getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            val result = getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase()
            if (result is Resource.Failure) {
                handleError(Exception("Something went wrong!"))
            }
        }
    }

    private fun getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            val result = getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase()
            if (result is Resource.Failure) {
                handleError(Exception("Something went wrong!"))
            }
        }
    }

}