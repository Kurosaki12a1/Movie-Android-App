package com.kuro.movie.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kuro.movie.data.data_source.local.AppDatabase
import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.data_source.local.dao.movie.UpComingDao
import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import com.kuro.movie.data.data_source.remote.DetailAPI
import com.kuro.movie.data.data_source.remote.ExploreAPI
import com.kuro.movie.data.data_source.remote.GenreAPI
import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.data.data_source.remote.PersonAPI
import com.kuro.movie.data.data_source.remote.UpComingAPI
import com.kuro.movie.data.preferences.AppPreferences
import com.kuro.movie.data.repository.AuthRepositoryImpl
import com.kuro.movie.data.repository.ExploreRepositoryImpl
import com.kuro.movie.data.repository.GenreRepositoryImpl
import com.kuro.movie.data.repository.HomeMovieRepositoryImpl
import com.kuro.movie.data.repository.HomeTvRepositoryImpl
import com.kuro.movie.data.repository.NetworkConnectivityObserver
import com.kuro.movie.data.repository.SharedPreferencesRepositoryImpl
import com.kuro.movie.data.repository.UpComingRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.LocalDatabaseRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.MovieLocalRepositoryImpl
import com.kuro.movie.data.repository.data_source.local.TvSeriesLocalRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.ExploreMovieRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.ExploreMultiSearchRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.ExploreTvRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.HomeMovieRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.HomeTvSeriesRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.MovieDetailRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.TvDetailRemoteRepositoryImpl
import com.kuro.movie.data.repository.data_source.remote.UpComingMovieRemoteRepositoryImpl
import com.kuro.movie.data.repository.detail.MovieDetailRepositoryImpl
import com.kuro.movie.data.repository.detail.TvDetailRepositoryImpl
import com.kuro.movie.data.repository.firebase.FirebaseCoreMovieRepositoryImpl
import com.kuro.movie.data.repository.firebase.FirebaseCoreRepositoryImpl
import com.kuro.movie.data.repository.firebase.FirebaseCoreTvSeriesRepositoryImpl
import com.kuro.movie.data.repository.firebase.FirebaseMovieRepositoryImpl
import com.kuro.movie.data.repository.firebase.FirebaseTvSeriesRepositoryImpl
import com.kuro.movie.data.repository.peson_detail.PersonRemoteDataSourceImpl
import com.kuro.movie.data.repository.peson_detail.PersonRepositoryImpl
import com.kuro.movie.domain.repository.AuthRepository
import com.kuro.movie.domain.repository.ConnectivityObserver
import com.kuro.movie.domain.repository.ExploreRepository
import com.kuro.movie.domain.repository.GenreRepository
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.domain.repository.PersonRepository
import com.kuro.movie.domain.repository.SharedPreferenceRepository
import com.kuro.movie.domain.repository.UpComingRepository
import com.kuro.movie.domain.repository.data_source.local.LocalDatabaseRepository
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreMovieRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreMultiSearchRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.ExploreTvRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeTvSeriesRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.MovieDetailRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.TvDetailRemoteRepository
import com.kuro.movie.domain.repository.data_source.remote.UpComingMovieRemoteRepository
import com.kuro.movie.domain.repository.detail.MovieDetailRepository
import com.kuro.movie.domain.repository.detail.TvDetailRepository
import com.kuro.movie.domain.repository.firebase.FirebaseCoreMovieRepository
import com.kuro.movie.domain.repository.firebase.FirebaseCoreRepository
import com.kuro.movie.domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.kuro.movie.domain.repository.firebase.FirebaseMovieRepository
import com.kuro.movie.domain.repository.firebase.FirebaseTvSeriesRepository
import com.kuro.movie.domain.repository.person_detail.PersonRemoteDataSource
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

    @Singleton
    @Provides
    fun provideSharedPreferenceRepository(appPreferences: AppPreferences): SharedPreferenceRepository =
        SharedPreferencesRepositoryImpl(appPreferences)

    @Singleton
    @Provides
    fun provideFirebaseCoreMovieRepository(
        context: Context,
        fireStore: FirebaseFirestore
    ): FirebaseCoreMovieRepository =
        FirebaseCoreMovieRepositoryImpl(context, fireStore)

    @Singleton
    @Provides
    fun provideFirebaseCoreTvSeriesRepository(
        context: Context,
        fireStore: FirebaseFirestore
    ): FirebaseCoreTvSeriesRepository =
        FirebaseCoreTvSeriesRepositoryImpl(context, fireStore)

    @Singleton
    @Provides
    fun provideFirebaseCoreRepository(
        fireStore: FirebaseFirestore
    ): FirebaseCoreRepository = FirebaseCoreRepositoryImpl(fireStore)

    @Singleton
    @Provides
    fun provideExploreMovieRemoteRepository(
        exploreAPI: ExploreAPI
    ): ExploreMovieRemoteRepository = ExploreMovieRemoteRepositoryImpl(exploreAPI)

    @Singleton
    @Provides
    fun provideExploreTvRemoteRepository(
        exploreAPI: ExploreAPI
    ): ExploreTvRemoteRepository = ExploreTvRemoteRepositoryImpl(exploreAPI)

    @Singleton
    @Provides
    fun provideExploreMultiSearchRepository(
        exploreAPI: ExploreAPI
    ): ExploreMultiSearchRemoteRepository = ExploreMultiSearchRepositoryImpl(exploreAPI)

    @Singleton
    @Provides
    fun provideExploreRepository(
        exploreMovieRemoteRepository: ExploreMovieRemoteRepository,
        exploreTvRemoteRepository: ExploreTvRemoteRepository,
        exploreMultiSearchRemoteRepository: ExploreMultiSearchRemoteRepository
    ): ExploreRepository = ExploreRepositoryImpl(
        exploreMovieRemoteRepository,
        exploreTvRemoteRepository,
        exploreMultiSearchRemoteRepository
    )

    @Provides
    @Singleton
    fun provideConnectivityObserver(context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context)

    @Provides
    @Singleton
    fun provideUpComingMovieRemoteRepository(upComingAPI: UpComingAPI): UpComingMovieRemoteRepository =
        UpComingMovieRemoteRepositoryImpl(upComingAPI)

    @Provides
    @Singleton
    fun provideUpComingRepository(
        upComingMovieRemoteRepository: UpComingMovieRemoteRepository,
        upComingDao: UpComingDao
    ): UpComingRepository =
        UpComingRepositoryImpl(upComingMovieRemoteRepository, upComingDao)

    @Provides
    @Singleton
    fun provideMovieDetailRemoteRepository(
        detailAPI: DetailAPI
    ): MovieDetailRemoteRepository = MovieDetailRemoteRepositoryImpl(detailAPI)

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        repository: MovieDetailRemoteRepository
    ): MovieDetailRepository = MovieDetailRepositoryImpl(repository)

    @Provides
    @Singleton
    fun provideTvDetailRemoteRepository(
        detailAPI: DetailAPI
    ): TvDetailRemoteRepository = TvDetailRemoteRepositoryImpl(detailAPI)

    @Provides
    @Singleton
    fun provideTvDetailRepository(
        repository: TvDetailRemoteRepository
    ): TvDetailRepository = TvDetailRepositoryImpl(repository)

    @Provides
    @Singleton
    fun providePersonRemoteDataSource(
        personAPI: PersonAPI
    ): PersonRemoteDataSource = PersonRemoteDataSourceImpl(personAPI)

    @Provides
    @Singleton
    fun providePersonRepository(
        personRemoteDataSource: PersonRemoteDataSource
    ): PersonRepository = PersonRepositoryImpl(personRemoteDataSource)
}