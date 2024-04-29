package com.kuro.movie.data.data_source.local.dao.tvseries

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kuro.movie.data.model.entity.tvseries.FavoriteTvSeries
import com.kuro.movie.data.model.entity.tvseries.TvSeriesWatchListItem
import com.kuro.movie.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME
import com.kuro.movie.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME
import io.reactivex.Flowable

@Dao
interface TvSeriesDao {

    @Insert
    suspend fun insertTvSeriesToFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    @Insert
    suspend fun insertTvSeriesToWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    @Delete
    suspend fun deleteTvSeriesFromFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    @Delete
    suspend fun deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    @Query("SELECT tvSeriesId FROM $FAVORITE_TV_SERIES_TABLE_NAME")
    fun getFavoriteTvSeriesIds(): Flowable<List<Int>>

    @Query("SELECT tvSeriesId FROM $TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME")
    fun getTvSeriesWatchListItemIds(): Flowable<List<Int>>

    @Query("SELECT * FROM $FAVORITE_TV_SERIES_TABLE_NAME")
    fun getFavoriteTvSeries(): Flowable<List<FavoriteTvSeries>>

    @Query("SELECT * FROM $TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME")
    fun getTvSeriesWatchList(): Flowable<List<TvSeriesWatchListItem>>

}