package com.kuro.movie.presenter.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.util.Constants.SPLASH_SCREEN_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel() {
    private val _navigateRoute = MutableLiveData(SplashRoute.DEFAULT)
    val navigateRoute: LiveData<SplashRoute>
        get() = _navigateRoute

    fun startTimer() {
        Completable.timer(SPLASH_SCREEN_DELAY, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onComplete() {
                    if (auth.currentUser != null) {
                        _navigateRoute.postValue(SplashRoute.HOME)
                    } else {
                        _navigateRoute.postValue(SplashRoute.AUTH)
                    }
                }
                override fun onError(e: Throwable) {}
            })
    }
}