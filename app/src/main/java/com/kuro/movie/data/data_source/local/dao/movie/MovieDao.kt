package com.kuro.movie.data.data_source.local.dao.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kuro.movie.data.model.entity.movie.FavoriteMovie
import com.kuro.movie.data.model.entity.movie.MovieWatchListItem
import com.kuro.movie.util.Constants.FAVORITE_MOVIE_TABLE_NAME
import com.kuro.movie.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovieToFavoriteList(favoriteMovie: FavoriteMovie)

    @Insert
    suspend fun insertMovieToWatchListItem(movieWatchListItem: MovieWatchListItem)

    @Delete
    suspend fun deleteMovieFromFavoriteList(favoriteMovie: FavoriteMovie)

    @Delete
    suspend fun deleteMovieFromWatchListItem(movieWatchListItem: MovieWatchListItem)

    @Query("SELECT movieId FROM $FAVORITE_MOVIE_TABLE_NAME")
    fun getFavoriteMovieIds(): List<Int>

    @Query("SELECT movieId FROM $MOVIE_WATCH_LIST_ITEM_TABLE_NAME")
    fun getMovieWatchListItemIds(): List<Int>

    @Query("SELECT * FROM $FAVORITE_MOVIE_TABLE_NAME")
    fun getFavoriteMovies(): List<FavoriteMovie>

    @Query("SELECT * FROM $MOVIE_WATCH_LIST_ITEM_TABLE_NAME")
    fun getMovieWatchList(): List<MovieWatchListItem>

}