package com.kuro.movie.data.model.entity.tvseries

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME

@Entity(tableName = TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME)
data class TvSeriesWatchListItem(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)
