package com.kuro.movie.di

import android.app.NotificationManager
import android.content.Context
import com.kuro.movie.framework.notification.UpComingMovieReminderNotification
import com.kuro.movie.framework.notification.WeekendNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    @Provides
    @Singleton
    fun provideUpComingMovieReminderNotification(
        context: Context,
        notificationManager: NotificationManager
    ): UpComingMovieReminderNotification =
        UpComingMovieReminderNotification(context, notificationManager)

    @Provides
    @Singleton
    fun provideWeekendNotification(
        context: Context,
        notificationManager: NotificationManager
    ): WeekendNotification = WeekendNotification(context, notificationManager)
}
