package com.kuro.movie.data.repository.data_source.local

import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.mapper.toFavoriteMovie
import com.kuro.movie.data.mapper.toMovieWatchListItem
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalRepository {

    override suspend fun insertMovieToFavoriteList(movie: Movie) {
        movieDao.insertMovieToFavoriteList(
            favoriteMovie = movie.toFavoriteMovie()
        )
    }

    override suspend fun insertMovieToWatchList(movie: Movie) {
        movieDao.insertMovieToWatchListItem(
            movieWatchListItem = movie.toMovieWatchListItem()
        )
    }

    override suspend fun deleteMovieFromFavoriteList(movie: Movie) {
        movieDao.deleteMovieFromFavoriteList(favoriteMovie = movie.toFavoriteMovie())
    }

    override suspend fun deleteMovieFromWatchListItem(movie: Movie) {
        movieDao.deleteMovieFromWatchListItem(movieWatchListItem = movie.toMovieWatchListItem())
    }

    override fun getFavoriteMovieIds(): Flowable<List<Int>> {
        return movieDao.getFavoriteMovieIds().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieWatchListItemIds(): Flowable<List<Int>> {
        return movieDao.getMovieWatchListItemIds().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavoriteMovies(): Flowable<List<Movie>> {
        return movieDao.getFavoriteMovies().map {
            it.map { it.movie }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMoviesInWatchList(): Flowable<List<Movie>> {
        return movieDao.getMovieWatchList().map {
            it.map { it.movie }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}