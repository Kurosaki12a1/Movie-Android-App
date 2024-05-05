package com.kuro.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import com.kuro.movie.data.data_source.local.dao.movie.UpComingDao
import com.kuro.movie.data.model.entity.movie.UpComingRemindEntity
import com.kuro.movie.data.paging_source.UpComingMoviePagingSource
import com.kuro.movie.domain.model.UpComingMovie
import com.kuro.movie.domain.repository.UpComingRepository
import com.kuro.movie.domain.repository.data_source.remote.UpComingMovieRemoteRepository
import com.kuro.movie.util.Constants
import io.reactivex.Observable
import javax.inject.Inject

class UpComingRepositoryImpl @Inject constructor(
    private val upComingMovieRemoteRepository: UpComingMovieRemoteRepository,
    private val upComingDao: UpComingDao
) : UpComingRepository {

    override fun getUpComingMovies(language: String): Observable<PagingData<UpComingMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpComingMoviePagingSource(
                    fetchUpcomingMovie = { page ->
                        upComingMovieRemoteRepository.getUpComingMovies(
                            page = page,
                            language = language
                        ).results
                    }
                )
            }
        ).observable
    }

    override suspend fun insertUpcomingRemind(upcomingMovie: UpComingMovie) {
        upComingDao.insertUpcomingRemind(upcomingMovie.toUpComingRemindEntity())
    }

    override suspend fun deleteUpcomingRemind(upcomingMovie: UpComingMovie) {
        upComingDao.deleteUpcomingRemind(upcomingMovie.toUpComingRemindEntity())
    }

    override fun getAllUpcomingRemind(): List<UpComingRemindEntity> {
        return upComingDao.getUpcomingRemindList()
    }

    private fun UpComingMovie.toUpComingRemindEntity(): UpComingRemindEntity {
        return UpComingRemindEntity(
            movieId = movie.id,
            movieTitle = movie.title,
            movieReleaseDate = movie.fullReleaseDate ?: ""
        )
    }
}