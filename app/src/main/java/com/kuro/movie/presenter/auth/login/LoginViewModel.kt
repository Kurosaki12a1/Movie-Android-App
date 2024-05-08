package com.kuro.movie.presenter.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.firebase.FirebaseUseCases
import com.kuro.movie.domain.usecase.auth.login.SignInWithCredentialUseCase
import com.kuro.movie.domain.usecase.auth.login.SignInWithEmailAndPasswordUseCase
import com.kuro.movie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val firebaseUseCases: FirebaseUseCases,
    private val auth: FirebaseAuth
) : BaseViewModel() {

    private val mutableState = MutableLiveData<Resource<Unit>>()
    val uiState: LiveData<Resource<Unit>>
        get() = mutableState

    init {
        checkUserSignIn()
    }

    private fun checkUserSignIn() {
        if (auth.currentUser != null) {
            mutableState.value = Resource.Loading
            onLoginSuccess()
        }
    }

    fun signIn(email: String, password: String) {
        mutableState.value = Resource.Loading
        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase(email, password)
                .subscribeOn(Schedulers.io())
                .subscribe({ onLoginSuccess() }, { error ->
                    mutableState.value = Resource.Failure(error)
                    handleError(error)
                }
                ).let { addDisposable(it) }
        }
    }

    fun signInWithGoogle(task: Task<GoogleSignInAccount>) {
        mutableState.value = Resource.Loading
        viewModelScope.launch {
            if (task.isSuccessful) {
                signInWithCredentialUseCase(task)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ onLoginSuccess() },
                        { error ->
                        mutableState.value = Resource.failure(error)
                        handleError(error)
                    }).let { addDisposable(it) }
            } else {
                mutableState.value = Resource.failure(task.exception ?: Exception("Unknown error"))
            }
        }
    }

    // Handle FireStore Firebase database add movie
    private fun onLoginSuccess() {
        viewModelScope.launch {
            firebaseUseCases.getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase()
            mutableState.postValue(Resource.success(Unit))
        }
    }
}