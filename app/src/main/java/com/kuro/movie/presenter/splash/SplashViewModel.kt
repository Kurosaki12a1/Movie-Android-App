package com.kuro.movie.presenter.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.util.Constants.SPLASH_SCREEN_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit

class SplashViewModel : BaseViewModel() {
    private val _navigateToHome = MutableLiveData<Boolean>(false)
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    fun startTimer() {
        Completable.timer(
            SPLASH_SCREEN_DELAY,
            TimeUnit.MILLISECONDS,
            AndroidSchedulers.mainThread()
        ).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onComplete() { _navigateToHome.value = true }

                override fun onError(e: Throwable) {}
            })
    }
}