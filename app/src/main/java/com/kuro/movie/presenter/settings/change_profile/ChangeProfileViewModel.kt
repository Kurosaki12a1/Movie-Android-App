package com.kuro.movie.presenter.settings.change_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.auth.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel() {

    private val _mutableState = MutableLiveData(ChangeProfileUIState())
    val state: LiveData<ChangeProfileUIState>
        get() = _mutableState

    private val _updateProfileStatus = MutableLiveData(false)
    val updateProfileStatus: LiveData<Boolean>
        get() = _updateProfileStatus

    init {
        initState()
    }

    private fun initState() {
        _mutableState.postValue(
            _mutableState.value?.copy(
                currentProfile = auth.currentUser,
                newDisplayName = auth.currentUser?.displayName ?: "",
                photoUri = auth.currentUser?.photoUrl
            )
        )
    }

    fun submit(displayName: String, photoUri: Uri? = null) {
        val user = auth.currentUser ?: return
        _mutableState.postValue(_mutableState.value?.copy(isLoading = true))
        if (user.displayName == displayName && user.photoUrl == photoUri) {
            _mutableState.postValue(_mutableState.value?.copy(isLoading = false))
            sendMessage("Nothing to update")
            return
        }
        viewModelScope.launch {
            updateProfileUseCase(displayName, photoUri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _mutableState.postValue(_mutableState.value?.copy(isLoading = false))
                    _updateProfileStatus.postValue(true)
                    sendMessage("Update profile successful")
                }, {
                    _mutableState.postValue(_mutableState.value?.copy(isLoading = false))
                    handleError(it)
                }).let { addDisposable(it) }
        }
    }

}