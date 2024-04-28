package com.kuro.movie.di

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.data.repository.AuthRepositoryImpl
import com.kuro.movie.data.repository.HomeMovieRepositoryImpl
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.domain.repository.HomeMovieRepository
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
    fun provideHomeMovieRepository(homeAPI: HomeAPI): HomeMovieRepository =
        HomeMovieRepositoryImpl(homeAPI)


    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)
}