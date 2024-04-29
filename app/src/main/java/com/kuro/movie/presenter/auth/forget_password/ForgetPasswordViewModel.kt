package com.kuro.movie.presenter.auth.forget_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.forgetpassword.SendPasswordResetEmailUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
) : BaseViewModel() {

    private val mutableState = MutableLiveData<Resource<Unit>>()
    val uiState: LiveData<Resource<Unit>>
        get() = mutableState

    private val mutableEmailState = MutableLiveData<String>()
    val emailState: LiveData<String>
        get() = mutableEmailState

    init {
        ForgetPasswordFragmentArgs.fromSavedStateHandle(savedStateHandle).email?.let { email ->
            mutableEmailState.postValue(email)
        }
    }

    fun sendPasswordResetToEmail(email: String) {
        mutableState.value = Resource.Loading
        viewModelScope.launch {
            sendPasswordResetEmailUseCase(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mutableState.value = Resource.Success(Unit)
                    sendMessage(Constants.MESSAGE_SEND_EMAIL_SUCCESSFUL)
                }, { error ->
                    mutableState.value = Resource.failure(error)
                    handleError(error)
                }).let { addDisposable(it) }
        }
    }
}