package com.kuro.movie.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.kuro.movie.domain.repository.ConnectivityObserver
import io.reactivex.Observable
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(context: Context) : ConnectivityObserver {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Observable<ConnectivityObserver.Status> {
        return Observable.create<ConnectivityObserver.Status?> { emitter ->
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    emitter.onNext(ConnectivityObserver.Status.AVAILABLE)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    emitter.onNext(ConnectivityObserver.Status.LOSING)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    emitter.onNext(ConnectivityObserver.Status.LOST)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    emitter.onNext(ConnectivityObserver.Status.UNAVAILABLE)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            emitter.setCancellable {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}