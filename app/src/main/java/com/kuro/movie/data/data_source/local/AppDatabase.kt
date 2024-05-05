package com.kuro.movie.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kuro.movie.data.data_source.local.dao.movie.MovieDao
import com.kuro.movie.data.data_source.local.dao.movie.UpComingDao
import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import com.kuro.movie.data.model.entity.converter.ListIntConverter
import com.kuro.movie.data.model.entity.movie.FavoriteMovie
import com.kuro.movie.data.model.entity.movie.MovieWatchListItem
import com.kuro.movie.data.model.entity.movie.UpComingRemindEntity
import com.kuro.movie.data.model.entity.tvseries.FavoriteTvSeries
import com.kuro.movie.data.model.entity.tvseries.TvSeriesWatchListItem

@Database(
    entities = [
        FavoriteMovie::class, MovieWatchListItem::class,
        FavoriteTvSeries::class, TvSeriesWatchListItem::class,
        UpComingRemindEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(ListIntConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "kuro_movie_db"
    }

    abstract fun movieDao(): MovieDao

    abstract fun tvSeriesDao(): TvSeriesDao

    abstract fun upcomingDao(): UpComingDao
}