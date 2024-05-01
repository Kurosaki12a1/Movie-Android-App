package com.kuro.movie.di

import com.google.firebase.auth.FirebaseAuth
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.domain.repository.GenreRepository
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.domain.repository.SharedPreferenceRepository
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseCoreMovieRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseCoreTvSeriesRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseMovieRepository
import com.kuro.movie.domain.repository.data_source.remote.FirebaseTvSeriesRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.domain.usecase.auth.ChangePasswordUseCase
import com.kuro.movie.domain.usecase.auth.DeleteAccountUseCase
import com.kuro.movie.domain.usecase.auth.UpdateProfileUseCase
import com.kuro.movie.domain.usecase.auth.login.SignInWithCredentialUseCase
import com.kuro.movie.domain.usecase.auth.login.SignInWithEmailAndPasswordUseCase
import com.kuro.movie.domain.usecase.auth.signup.CreateUserWithEmailAndPasswordUseCase
import com.kuro.movie.domain.usecase.database.ClearAllDatabaseUseCase
import com.kuro.movie.domain.usecase.database.LocalDatabaseUseCases
import com.kuro.movie.domain.usecase.firebase.AddMovieToFavoriteListInFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.AddMovieToWatchListInFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.AddTvSeriesToWatchListInFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.FirebaseCoreUseCases
import com.kuro.movie.domain.usecase.firebase.FirebaseUseCases
import com.kuro.movie.domain.usecase.firebase.GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.kuro.movie.domain.usecase.firebase.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.firebase.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.firebase.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import com.kuro.movie.domain.usecase.movie.GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.movie.GetFavoriteMovieIdsUseCase
import com.kuro.movie.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieGenreListUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieWatchListItemIdsUseCase
import com.kuro.movie.domain.usecase.movie.GetMoviesInWatchListUseCase
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetNowPlayingMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetPopularMoviesUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMovieUseCase
import com.kuro.movie.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.kuro.movie.domain.usecase.movie.ToggleMovieForFavoriteListUseCase
import com.kuro.movie.domain.usecase.movie.ToggleMovieForWatchListUseCase
import com.kuro.movie.domain.usecase.settings.SettingsUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesIdsUseCase
import com.kuro.movie.domain.usecase.tvseries.GetFavoriteTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetPopularTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTopRatedTvSeriesUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvGenreListUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesInWatchListUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.kuro.movie.domain.usecase.tvseries.GetTvSeriesWatchListItemIdsUseCase
import com.kuro.movie.domain.usecase.tvseries.ToggleTvSeriesForFavoriteListUseCase
import com.kuro.movie.domain.usecase.tvseries.ToggleTvSeriesForWatchListItemUseCase
import com.kuro.movie.domain.usecase.ui_mode.GetUIModeUseCase
import com.kuro.movie.domain.usecase.ui_mode.UpdateUIModeUseCase
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

    @Provides
    @Singleton
    fun provideClearAllDatabaseUseCase(
        repository: LocalDatabaseRepository
    ): ClearAllDatabaseUseCase = ClearAllDatabaseUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleMovieForFavoriteListUseCase(
        repository: MovieLocalRepository
    ): ToggleMovieForFavoriteListUseCase = ToggleMovieForFavoriteListUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleMovieForWatchListUseCase(
        repository: MovieLocalRepository
    ): ToggleMovieForWatchListUseCase = ToggleMovieForWatchListUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoriteMovieIdsUseCase(
        repository: MovieLocalRepository
    ): GetFavoriteMovieIdsUseCase = GetFavoriteMovieIdsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMovieWatchListItemIdsUseCase(
        repository: MovieLocalRepository
    ): GetMovieWatchListItemIdsUseCase = GetMovieWatchListItemIdsUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleTvSeriesForFavoriteListUseCase(
        repository: TvSeriesLocalRepository
    ): ToggleTvSeriesForFavoriteListUseCase = ToggleTvSeriesForFavoriteListUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleTvSeriesForWatchListItemUseCase(
        repository: TvSeriesLocalRepository
    ): ToggleTvSeriesForWatchListItemUseCase = ToggleTvSeriesForWatchListItemUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoriteTvSeriesIdsUseCase(
        repository: TvSeriesLocalRepository
    ): GetFavoriteTvSeriesIdsUseCase = GetFavoriteTvSeriesIdsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTvSeriesWatchListItemIdsUseCase(
        repository: TvSeriesLocalRepository
    ): GetTvSeriesWatchListItemIdsUseCase = GetTvSeriesWatchListItemIdsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoriteMoviesUseCase(
        repository: MovieLocalRepository
    ): GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMoviesInWatchListUseCase(
        repository: MovieLocalRepository
    ): GetMoviesInWatchListUseCase = GetMoviesInWatchListUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoriteTvSeriesUseCase(
        repository: TvSeriesLocalRepository
    ): GetFavoriteTvSeriesUseCase = GetFavoriteTvSeriesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTvSeriesInWatchListUseCase(
        repository: TvSeriesLocalRepository
    ): GetTvSeriesInWatchListUseCase = GetTvSeriesInWatchListUseCase(repository)

    @Provides
    @Singleton
    fun provideLocalDatabaseUseCase(
        clearAllDatabaseUseCase: ClearAllDatabaseUseCase,
        toggleMovieForFavoriteListUseCase: ToggleMovieForFavoriteListUseCase,
        toggleMovieForWatchListUseCase: ToggleMovieForWatchListUseCase,
        getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
        getMovieWatchListItemIdsUseCase: GetMovieWatchListItemIdsUseCase,
        toggleTvSeriesForFavoriteListUseCase: ToggleTvSeriesForFavoriteListUseCase,
        toggleTvSeriesForWatchListItemUseCase: ToggleTvSeriesForWatchListItemUseCase,
        getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
        getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase,
        getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
        getFavoriteTvSeriesUseCase: GetFavoriteTvSeriesUseCase,
        getMoviesInWatchListUseCase: GetMoviesInWatchListUseCase,
        getTvSeriesInWatchListUseCase: GetTvSeriesInWatchListUseCase
    ): LocalDatabaseUseCases = LocalDatabaseUseCases(
        clearAllDatabaseUseCase,
        toggleMovieForFavoriteListUseCase,
        toggleMovieForWatchListUseCase,
        getFavoriteMovieIdsUseCase,
        getMovieWatchListItemIdsUseCase,
        toggleTvSeriesForFavoriteListUseCase,
        toggleTvSeriesForWatchListItemUseCase,
        getFavoriteTvSeriesIdsUseCase,
        getTvSeriesWatchListItemIdsUseCase,
        getFavoriteMoviesUseCase,
        getFavoriteTvSeriesUseCase,
        getMoviesInWatchListUseCase,
        getTvSeriesInWatchListUseCase
    )

    @Provides
    @Singleton
    fun provideSettingsUseCase(
        repo: SharedPreferenceRepository
    ): SettingsUseCase = SettingsUseCase(GetUIModeUseCase(repo), UpdateUIModeUseCase(repo))

    @Provides
    @Singleton
    fun provideAddTvSeriesToWatchListInFirebaseUseCase(
        auth: FirebaseAuth,
        repository: FirebaseCoreTvSeriesRepository
    ): AddTvSeriesToWatchListInFirebaseUseCase = AddTvSeriesToWatchListInFirebaseUseCase(
        auth, repository
    )

    @Provides
    @Singleton
    fun provideAddTvSeriesToFavoriteListInFirebaseUseCase(
        auth: FirebaseAuth,
        repository: FirebaseCoreTvSeriesRepository
    ): AddTvSeriesToFavoriteListInFirebaseUseCase = AddTvSeriesToFavoriteListInFirebaseUseCase(
        auth, repository
    )

    @Provides
    @Singleton
    fun provideAddMovieToFavoriteListInFirebaseUseCase(
        auth: FirebaseAuth,
        repository: FirebaseCoreMovieRepository
    ): AddMovieToFavoriteListInFirebaseUseCase = AddMovieToFavoriteListInFirebaseUseCase(
        auth, repository
    )

    @Provides
    @Singleton
    fun provideAddMovieToWatchListInFirebaseUseCase(
        auth: FirebaseAuth,
        repository: FirebaseCoreMovieRepository
    ): AddMovieToWatchListInFirebaseUseCase = AddMovieToWatchListInFirebaseUseCase(
        auth, repository
    )

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCase(
        addMovieToFavoriteListInFirebaseUseCase: AddMovieToFavoriteListInFirebaseUseCase,
        addMovieToWatchListInFirebaseUseCase: AddMovieToWatchListInFirebaseUseCase,
        addTvSeriesToFavoriteListInFirebaseUseCase: AddTvSeriesToFavoriteListInFirebaseUseCase,
        addTvSeriesToWatchListInFirebaseUseCase: AddTvSeriesToWatchListInFirebaseUseCase
    ): FirebaseCoreUseCases = FirebaseCoreUseCases(
        addMovieToFavoriteListInFirebaseUseCase,
        addMovieToWatchListInFirebaseUseCase,
        addTvSeriesToFavoriteListInFirebaseUseCase,
        addTvSeriesToWatchListInFirebaseUseCase
    )

    @Provides
    @Singleton
    fun provideGetMovieWatchListFromLocalDatabaseThenUpdateToFirebase(
        localDatabaseUseCases: LocalDatabaseUseCases,
        firebaseCoreUseCases: FirebaseCoreUseCases
    ): GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase =
        GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase(
            localDatabaseUseCases, firebaseCoreUseCases
        )

    @Provides
    @Singleton
    fun provideGetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase(
        localDatabaseUseCases: LocalDatabaseUseCases,
        firebaseCoreUseCases: FirebaseCoreUseCases
    ): GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase =
        GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase(
            localDatabaseUseCases, firebaseCoreUseCases
        )

    @Provides
    @Singleton
    fun provideGetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase(
        localDatabaseUseCases: LocalDatabaseUseCases,
        firebaseCoreUseCases: FirebaseCoreUseCases
    ): GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase =
        GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase(
            localDatabaseUseCases, firebaseCoreUseCases
        )

    @Provides
    @Singleton
    fun provideGetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase(
        localDatabaseUseCases: LocalDatabaseUseCases,
        firebaseCoreUseCases: FirebaseCoreUseCases
    ): GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase =
        GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase(
            localDatabaseUseCases, firebaseCoreUseCases
        )

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        repository: AuthRepository
    ): ChangePasswordUseCase = ChangePasswordUseCase(repository)


    @Provides
    @Singleton
    fun provideDeleteAccountUseCase(
        repository: AuthRepository
    ): DeleteAccountUseCase = DeleteAccountUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        repository: AuthRepository
    ) : UpdateProfileUseCase = UpdateProfileUseCase(repository)
}