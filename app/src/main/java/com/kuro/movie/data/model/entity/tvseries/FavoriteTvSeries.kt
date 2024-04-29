package com.kuro.movie.data.model.entity.tvseries

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME

@Entity(tableName = FAVORITE_TV_SERIES_TABLE_NAME)
data class FavoriteTvSeries(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)