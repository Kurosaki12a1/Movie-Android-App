package com.kuro.movie.presenter.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.login.SignInWithEmailAndPasswordUseCase
import com.kuro.movie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
) : BaseViewModel() {

    private val mutableState = MutableLiveData<Resource<Unit>>()
    val uiState: LiveData<Resource<Unit>>
        get() = mutableState

    fun signIn(email: String, password: String) {
        mutableState.value = Resource.Loading
        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { },
                    { error ->
                        mutableState.value = Resource.Failure(error)
                        handleError(error)
                    }
                ).let { addDisposable(it) }
        }
    }
}