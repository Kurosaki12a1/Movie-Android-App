package com.kuro.movie.data.model.entity.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuro.movie.data.model.Movie
import com.kuro.movie.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME

@Entity(tableName = MOVIE_WATCH_LIST_ITEM_TABLE_NAME)
data class MovieWatchListItem(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)