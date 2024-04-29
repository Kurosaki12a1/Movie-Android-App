package com.kuro.movie.di

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.domain.repository.GenreRepository
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseMovieRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseTvSeriesRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.domain.usecase.FirebaseUseCases
import com.kuro.movie.domain.usecase.auth.login.SignInWithCredentialUseCase
import com.kuro.movie.domain.usecase.auth.login.SignInWithEmailAndPasswordUseCase
import com.kuro.movie.domain.usecase.auth.signup.CreateUserWithEmailAndPasswordUseCase
import com.kuro.movie.domain.usecase.movie.GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieGenreListUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.tvseries.GetPopularTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTopRatedTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvGenreListUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetNowPlayingMovieUseCase(repository: HomeMovieRemoteRepository): GetNowPlayingMovieUseCase =
        GetNowPlayingMovieUseCase(repository)

    @Provides
    @Singleton
    fun provideGetPopularMovieUseCase(repository: HomeMovieRemoteRepository): GetPopularMovieUseCase =
        GetPopularMovieUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTopRatedMovieUseCase(repository: HomeMovieRemoteRepository): GetTopRatedMovieUseCase =
        GetTopRatedMovieUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInWithEmailAndPasswordUseCase(repository: AuthRepository): SignInWithEmailAndPasswordUseCase =
        SignInWithEmailAndPasswordUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInWithCredentialUseCase(repository: AuthRepository): SignInWithCredentialUseCase {
        return SignInWithCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateUserWithEmailAndPasswordUseCase(repository: AuthRepository): CreateUserWithEmailAndPasswordUseCase {
        return CreateUserWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
        auth: FirebaseAuth,
        firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase {
        return GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
            auth,
            firebaseTvSeriesRepository,
            localDatabaseRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
        auth: FirebaseAuth,
        firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase {
        return GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
            auth,
            firebaseTvSeriesRepository,
            localDatabaseRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
        auth: FirebaseAuth,
        firebaseMovieRepository: FirebaseMovieRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase {
        return GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
            auth,
            firebaseMovieRepository,
            localDatabaseRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
        auth: FirebaseAuth,
        firebaseMovieRepository: FirebaseMovieRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase {
        return GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
            auth,
            firebaseMovieRepository,
            localDatabaseRepository
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseUseCases(
        getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase: GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase,
        getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase: GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase,
        getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase: GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase,
        getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase: GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
    ): FirebaseUseCases = FirebaseUseCases(
        getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase,
        getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase,
        getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase,
        getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
    )

    @Provides
    @Singleton
    fun provideGetMovieGenreListUseCase(repo: GenreRepository): GetMovieGenreListUseCase =
        GetMovieGenreListUseCase(repo)

    @Provides
    @Singleton
    fun provideGetTvGenreListUseCase(repo: GenreRepository): GetTvGenreListUseCase =
        GetTvGenreListUseCase(repo)

    @Provides
    @Singleton
    fun provideGetNowPlayingMoviesUseCase(
        homeMovieRepository: HomeMovieRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase
    ): GetNowPlayingMoviesUseCase =
        GetNowPlayingMoviesUseCase(homeMovieRepository, getMovieGenreListUseCase)

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(
        homeMovieRepository: HomeMovieRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase
    ): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(homeMovieRepository, getMovieGenreListUseCase)

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(
        homeMovieRepository: HomeMovieRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase
    ): GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCase(homeMovieRepository, getMovieGenreListUseCase)

    @Provides
    @Singleton
    fun provideGetPopularTvSeriesUseCase(
        homeTvRepository: HomeTvRepository,
        getTvGenreListUseCase: GetTvGenreListUseCase
    ): GetPopularTvSeriesUseCase =
        GetPopularTvSeriesUseCase(homeTvRepository, getTvGenreListUseCase)

    @Provides
    @Singleton
    fun provideGetTopRatedTvSeriesUseCase(
        homeTvRepository: HomeTvRepository,
        getTvGenreListUseCase: GetTvGenreListUseCase
    ): GetTopRatedTvSeriesUseCase =
        GetTopRatedTvSeriesUseCase(homeTvRepository, getTvGenreListUseCase)
}