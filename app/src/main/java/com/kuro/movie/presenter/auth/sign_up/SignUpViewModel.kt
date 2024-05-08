package com.kuro.movie.presenter.auth.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.signup.CreateUserWithEmailAndPasswordUseCase
import com.kuro.movie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : BaseViewModel() {

    private val mutableState = MutableLiveData<Resource<Unit>>()
    val uiState: LiveData<Resource<Unit>>
        get() = mutableState

    fun createUserWithEmailAndPassword(email: String, password: String) {
        mutableState.value = Resource.Loading
        viewModelScope.launch {
            createUserWithEmailAndPasswordUseCase(email, password)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mutableState.postValue(Resource.success(Unit))
                }, { error ->
                    mutableState.postValue(Resource.failure(error))
                    handleError(error)
                }).let { addDisposable(it) }
        }
    }
}