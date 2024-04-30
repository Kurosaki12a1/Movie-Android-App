package com.kuro.movie.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    val snackBarMessage = MutableLiveData<String>()

    fun addDisposable(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    fun sendMessage(message: String) {
        snackBarMessage.postValue(message)
    }

    fun handleError(error: Throwable) {
        snackBarMessage.postValue(error.localizedMessage ?: "An unexpected error occurred")
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}