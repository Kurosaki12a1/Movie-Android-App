package com.kuro.movie.di

import android.app.Application
import androidx.room.Room
import com.kuro.movie.data.data_source.local.AppDatabase
import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.data_source.local.dao.movie.UpComingDao
import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    fun provideTvSeriesDao(appDatabase: AppDatabase): TvSeriesDao = appDatabase.tvSeriesDao()

    @Provides
    fun provideUpComingDao(appDatabase: AppDatabase): UpComingDao = appDatabase.upcomingDao()
}