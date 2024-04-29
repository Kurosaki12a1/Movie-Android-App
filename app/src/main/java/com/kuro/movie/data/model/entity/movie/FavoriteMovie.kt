package com.kuro.movie.data.model.entity.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuro.movie.data.model.Movie
import com.kuro.movie.util.Constants.FAVORITE_MOVIE_TABLE_NAME

@Entity(tableName = FAVORITE_MOVIE_TABLE_NAME)
data class FavoriteMovie(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)