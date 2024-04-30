package com.kuro.movie.data.repository.data_source.local

import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import com.kuro.movie.data.mapper.toFavoriteTvSeries
import com.kuro.movie.data.mapper.toTvSeriesWatchListItem
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvSeriesLocalRepositoryImpl @Inject constructor(
    private val tvSeriesDao: TvSeriesDao
) : TvSeriesLocalRepository {

    override suspend fun insertTvSeriesToFavoriteList(tvSeries: TvSeries) {
        tvSeriesDao.insertTvSeriesToFavoriteList(
            favoriteTvSeries = tvSeries.toFavoriteTvSeries()
        )
    }

    override suspend fun insertTvSeriesToWatchListItem(tvSeries: TvSeries) {
        tvSeriesDao.insertTvSeriesToWatchListItem(
            tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem()
        )
    }

    override suspend fun deleteTvSeriesFromFavoriteList(tvSeries: TvSeries) {
        tvSeriesDao.deleteTvSeriesFromFavoriteList(
            favoriteTvSeries = tvSeries.toFavoriteTvSeries()
        )
    }

    override suspend fun deleteTvSeriesFromWatchListItem(tvSeries: TvSeries) {
        tvSeriesDao.deleteTvSeriesFromWatchListItem(
            tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem()
        )
    }

    override suspend fun getFavoriteTvSeriesIds(): List<Int> {
        return withContext(Dispatchers.IO) {
            tvSeriesDao.getFavoriteTvSeriesIds()
        }
    }

    override suspend fun getTvSeriesWatchListItemIds(): List<Int> {
        return withContext(Dispatchers.IO) {
            tvSeriesDao.getTvSeriesWatchListItemIds()
        }
    }

    override suspend fun getFavoriteTvSeries(): List<TvSeries> {
        return withContext(Dispatchers.IO) {
            tvSeriesDao.getFavoriteTvSeries().map { it.tvSeries }
        }
    }

    override suspend fun getTvSeriesInWatchList(): List<TvSeries> {
        return withContext(Dispatchers.IO) {
            tvSeriesDao.getTvSeriesWatchList().map { it.tvSeries }
        }
    }
}