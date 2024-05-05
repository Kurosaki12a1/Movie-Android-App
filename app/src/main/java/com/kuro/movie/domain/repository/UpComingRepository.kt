package com.kuro.movie.domain.repository

import androidx.paging.PagingData
import com.kuro.movie.data.model.entity.movie.UpComingRemindEntity
import com.kuro.movie.domain.model.UpComingMovie
import io.reactivex.Observable

interface UpComingRepository {
    fun getUpComingMovies(
        language: String
    ): Observable<PagingData<UpComingMovie>>

    suspend fun insertUpcomingRemind(upcomingMovie: UpComingMovie)

    suspend fun deleteUpcomingRemind(upcomingMovie: UpComingMovie)

    fun getAllUpcomingRemind(): List<UpComingRemindEntity>
}