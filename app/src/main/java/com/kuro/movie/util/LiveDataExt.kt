package com.kuro.movie.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <T> Fragment.observerLiveData(
    liveData: LiveData<T>,
    action: suspend (T) -> Unit
) {
    return liveData.observe(viewLifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            action(it)
        }
    }
}

fun <T> Observable<T>.asLiveData(): LiveData<T> {
    return toFlowable(BackpressureStrategy.LATEST)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .toLiveData()
}
