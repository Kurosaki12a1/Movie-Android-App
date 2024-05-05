package com.kuro.movie.framework.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kuro.movie.R
import com.kuro.movie.core.BaseNotification
import javax.inject.Inject

class UpComingMovieReminderNotification @Inject constructor(
    private val context: Context,
    notificationManager: NotificationManager
) : BaseNotification(notificationManager) {
    override val notificationChannelId: String
        get() = "upcoming_movie_reminder_notification_channel_id"

    override val notificationChannelName: String
        get() = "Upcoming Movie Reminder"
    override val notificationId: Int
        get() = 100

    private var movieId: Int = -1
    private var movieTitle: String = ""

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    fun setMovieTitle(movieTitle: String) {
        this.movieTitle = movieTitle
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(
        importance: Int
    ): NotificationChannel {
        return super.createNotificationChannel(
            NotificationManager.IMPORTANCE_HIGH
        )
    }

    override fun buildNotification(
    ): Notification {
        val intent: Intent? = context.packageManager.getLaunchIntentForPackage(context.packageName)

        val pendingIntent = PendingIntent.getActivity(
            context, movieId, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setStyle(NotificationCompat.BigTextStyle().bigText(getNotificationMessageBigStyle()))
            .setSmallIcon(R.drawable.ic_outline_movie_red)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun getNotificationTitle() = context.getString(R.string.upcoming_movie_release_title)

    override fun getNotificationMessage() =
        context.getString(R.string.upcoming_movie_release_basic, movieTitle)

    private fun getNotificationMessageBigStyle() =
        context.getString(R.string.upcoming_movie_set_big_context, movieTitle)
}