package com.kuro.movie.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> Fragment.observerLiveData(
    liveData: LiveData<T>,
    action: suspend CoroutineScope.(T) -> Unit
) {
    return liveData.observe(viewLifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            action(it)
        }
    }
}

fun <T> MutableLiveData<T>.update(transform: (T) -> T) {
    this.value = this.value?.let(transform)
}

fun <T> MutableLiveData<T>.postUpdate(transform: (T) -> T) {
    this.postValue(this.value?.let(transform))
}


fun <T> Observable<T>.asLiveData(): LiveData<T> {
    return toFlowable(BackpressureStrategy.LATEST)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .toLiveData()
}
