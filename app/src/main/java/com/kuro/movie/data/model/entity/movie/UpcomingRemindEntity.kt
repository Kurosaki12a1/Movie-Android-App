package com.kuro.movie.data.model.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuro.movie.util.Constants.UPCOMING_REMIND_TABLE_NAME

@Entity(tableName = UPCOMING_REMIND_TABLE_NAME)
data class UpcomingRemindEntity(
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val movieTitle: String,
    val movieReleaseDate: String,
)