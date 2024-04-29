package com.kuro.movie.data.data_source.local.dao.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuro.movie.data.model.entity.movie.UpcomingRemindEntity
import com.kuro.movie.util.Constants.UPCOMING_REMIND_TABLE_NAME
import io.reactivex.Flowable


@Dao
interface UpComingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    @Delete
    suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    @Query("SELECT * FROM $UPCOMING_REMIND_TABLE_NAME")
    fun getUpcomingRemindList(): Flowable<List<UpcomingRemindEntity>>
}
