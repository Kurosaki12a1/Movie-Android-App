package com.kuro.movie.data.alarm_mananger

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.kuro.movie.domain.alarm_manager.UpComingAlarmScheduler
import com.kuro.movie.domain.model.UpComingAlarmItem
import com.kuro.movie.framework.receiver.UpComingAlarmReceiver
import com.kuro.movie.util.DateFormatUtils
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class UpComingAlarmSchedulerImpl @Inject constructor(
    private val context: Context
) : UpComingAlarmScheduler {

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
        const val EXTRA_MOVIE_TITLE = "movieTitle"
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun scheduleAlarm(item: UpComingAlarmItem) {
        val date =
            DateFormatUtils.convertToDateFromReleaseDate(releaseDate = item.movieReleaseDate)

        val triggerAtMillis = LocalDate.of(
            date.year,
            date.month,
            date.dayOfMonth
        ).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val intent = Intent(
            context,
            UpComingAlarmReceiver::class.java
        ).apply {
            putExtra(EXTRA_MOVIE_ID, item.movieId)
            putExtra(EXTRA_MOVIE_TITLE, item.movieTitle)
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            PendingIntent.getBroadcast(
                context,
                item.movieId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelAlarm(item: UpComingAlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.movieId,
                Intent(
                    context,
                    UpComingAlarmReceiver::class.java
                ),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}