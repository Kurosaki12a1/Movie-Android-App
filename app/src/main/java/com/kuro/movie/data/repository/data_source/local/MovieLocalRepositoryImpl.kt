package com.kuro.movie.data.repository.data_source.local

import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.mapper.toFavoriteMovie
import com.kuro.movie.data.mapper.toMovieWatchListItem
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.data_source.local.MovieLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun getFavoriteMovieIds(): List<Int> {
        return withContext(Dispatchers.IO) {
            movieDao.getFavoriteMovieIds()
        }
    }

    override suspend fun getMovieWatchListItemIds(): List<Int> {
        return withContext(Dispatchers.IO) {
            movieDao.getMovieWatchListItemIds()
        }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            movieDao.getFavoriteMovies().map { it.movie }
        }
    }

    override suspend fun getMoviesInWatchList(): List<Movie> {
        return withContext(Dispatchers.IO) {
            movieDao.getMovieWatchList().map { it.movie }
        }
    }
}