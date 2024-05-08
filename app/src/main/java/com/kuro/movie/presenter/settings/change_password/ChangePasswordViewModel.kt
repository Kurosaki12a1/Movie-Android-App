package com.kuro.movie.presenter.settings.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData(ChangePasswordUIState())
    val state: LiveData<ChangePasswordUIState>
        get() = _state

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        reEnterPassword: String
    ) {
        _state.postValue(_state.value?.copy(isLoading = true))
        viewModelScope.launch {
            changePasswordUseCase.invoke(oldPassword, newPassword, reEnterPassword)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _state.postValue(_state.value?.copy(isLoading = false))
                    _state.postValue(_state.value?.copy(isChangeSuccess = true))
                    sendMessage("Change password successfully")
                }, { error ->
                    _state.postValue(_state.value?.copy(isLoading = false))
                    handleError(error)
                }).let { addDisposable(it) }
        }
    }
}