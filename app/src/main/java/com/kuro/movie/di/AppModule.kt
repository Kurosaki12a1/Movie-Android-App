package com.kuro.movie.di

import android.app.Application
import android.content.Context
import com.kuro.movie.data.alarm_mananger.UpComingAlarmSchedulerImpl
import com.kuro.movie.data.preferences.AppPreferences
import com.kuro.movie.domain.alarm_manager.UpComingAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences =
        AppPreferences(context)

    @Provides
    @Singleton
    fun provideUpComingAlarmScheduler(context: Context): UpComingAlarmScheduler =
        UpComingAlarmSchedulerImpl(context)
}