package com.kuro.movie.framework.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kuro.movie.data.alarm_mananger.UpComingAlarmSchedulerImpl
import com.kuro.movie.framework.notification.UpComingMovieReminderNotification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpComingAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notification: UpComingMovieReminderNotification

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                val movieId = intent.getIntExtra(UpComingAlarmSchedulerImpl.EXTRA_MOVIE_ID, -1)
                val movieTitle = intent.getStringExtra(UpComingAlarmSchedulerImpl.EXTRA_MOVIE_TITLE)

                if (movieId == -1) return
                notification.setMovieId(movieId)
                notification.setMovieTitle(movieTitle ?: "")
                notification.showNotification()
            }
        } ?: return
    }
}