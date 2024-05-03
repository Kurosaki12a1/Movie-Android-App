package com.kuro.movie.domain.repository

import io.reactivex.Observable

interface ConnectivityObserver {

    fun observe(): Observable<Status>

    enum class Status {
        AVAILABLE,
        UNAVAILABLE,
        LOSING,
        LOST
    }
}

fun ConnectivityObserver.Status.isAvailable(): Boolean {
    return this == ConnectivityObserver.Status.AVAILABLE
}