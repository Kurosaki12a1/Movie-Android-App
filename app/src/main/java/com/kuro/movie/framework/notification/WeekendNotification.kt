package com.kuro.movie.framework.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kuro.movie.R
import com.kuro.movie.core.BaseNotification
import javax.inject.Inject

class WeekendNotification @Inject constructor(
    private val context: Context,
    notificationManager: NotificationManager
) : BaseNotification(notificationManager = notificationManager) {
    override val notificationChannelId: String
        get() = "weekend_notification_channel_id"
    override val notificationChannelName: String
        get() = "Weekend Notification"
    override val notificationId: Int
        get() = 101

    override fun buildNotification(): Notification {

        val intent: Intent? =
            context.packageManager.getLaunchIntentForPackage(context.packageName)

        val pendingIntent = PendingIntent.getActivity(
            context, 1, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(R.drawable.ic_outline_movie_red)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun getNotificationTitle(): String {
        return context.getString(R.string.have_good_weekend)
    }

    override fun getNotificationMessage(): String {
        return context.getString(R.string.movie_notification)
    }
}