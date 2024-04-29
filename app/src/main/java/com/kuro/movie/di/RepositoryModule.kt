package com.kuro.movie.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kuro.movie.data.data_source.local.AppDatabase
import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import com.kuro.movie.data.data_source.remote.GenreAPI
import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.data.repository.AuthRepositoryImpl
import com.kuro.movie.data.repository.GenreRepositoryImpl
import com.kuro.movie.data.repository.HomeMovieRepositoryImpl
import com.kuro.movie.data.repository.HomeTvRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.LocalDatabaseRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.MovieLocalRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.TvSeriesLocalRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.FirebaseMovieRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.FirebaseTvSeriesRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.HomeMovieRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.HomeTvSeriesRemoteRepositoryImpl
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.domain.repository.GenreRepository
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseMovieRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseTvSeriesRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeTvSeriesRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideHomeMovieRemoteRepository(homeAPI: HomeAPI): HomeMovieRemoteRepository =
        HomeMovieRemoteRepositoryImpl(homeAPI)

    @Singleton
    @Provides
    fun provideHomeTvSeriesRemoteRepository(homeAPI: HomeAPI): HomeTvSeriesRemoteRepository =
        HomeTvSeriesRemoteRepositoryImpl(homeAPI)


    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Singleton
    @Provides
    fun provideMovieLocalRepository(movieDao: MovieDao): MovieLocalRepository =
        MovieLocalRepositoryImpl(movieDao)

    @Singleton
    @Provides
    fun provideTvSeriesLocalRepository(tvSeriesDao: TvSeriesDao): TvSeriesLocalRepository =
        TvSeriesLocalRepositoryImpl(tvSeriesDao)

    @Singleton
    @Provides
    fun provideLocalDatabaseRepository(
        appDatabase: AppDatabase,
        movieRepository: MovieLocalRepository,
        tvSeriesRepository: TvSeriesLocalRepository
    ): LocalDatabaseRepository = LocalDatabaseRepositoryImpl(
        appDatabase,
        movieRepository,
        tvSeriesRepository
    )

    @Singleton
    @Provides
    fun provideFirebaseMovieRepository(
        context: Context,
        firestore: FirebaseFirestore
    ): FirebaseMovieRepository = FirebaseMovieRepositoryImpl(context, firestore)

    @Singleton
    @Provides
    fun provideFirebaseTvSeriesRepository(
        context: Context,
        firestore: FirebaseFirestore
    ): FirebaseTvSeriesRepository = FirebaseTvSeriesRepositoryImpl(context, firestore)

    @Singleton
    @Provides
    fun provideGenreRepository(genreAPI: GenreAPI): GenreRepository = GenreRepositoryImpl(genreAPI)

    @Singleton
    @Provides
    fun provideHomeMovieRepository(dataSource: HomeMovieRemoteRepository): HomeMovieRepository =
        HomeMovieRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideHomeTvRepository(dataSource: HomeTvSeriesRemoteRepository): HomeTvRepository =
        HomeTvRepositoryImpl(dataSource)


}