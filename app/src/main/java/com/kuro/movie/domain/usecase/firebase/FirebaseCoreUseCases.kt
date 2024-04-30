package com.kuro.movie.domain.usecase.firebase

data class FirebaseCoreUseCases(
    val addMovieToFavoriteListInFirebaseUseCase: AddMovieToFavoriteListInFirebaseUseCase,
    val addMovieToWatchListInFirebaseUseCase: AddMovieToWatchListInFirebaseUseCase,
    val addTvSeriesToFavoriteListInFirebaseUseCase: AddTvSeriesToFavoriteListInFirebaseUseCase,
    val addTvSeriesToWatchListInFirebaseUseCase: AddTvSeriesToWatchListInFirebaseUseCase
)