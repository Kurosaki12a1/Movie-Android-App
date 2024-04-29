package com.kuro.movie.data.repository.data_source.local

import com.kuro.movie.data.data_source.local.dao.tvseries.TvSeriesDao
import com.kuro.movie.data.mapper.toFavoriteTvSeries
import com.kuro.movie.data.mapper.toTvSeriesWatchListItem
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.data_source.local.TvSeriesLocalRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    override fun getFavoriteTvSeriesIds(): Flowable<List<Int>> {
        return tvSeriesDao.getFavoriteTvSeriesIds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTvSeriesWatchListItemIds(): Flowable<List<Int>> {
        return tvSeriesDao.getTvSeriesWatchListItemIds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavoriteTvSeries(): Flowable<List<TvSeries>> {
        return tvSeriesDao.getFavoriteTvSeries()
            .map { it.map { it.tvSeries } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTvSeriesInWatchList(): Flowable<List<TvSeries>> {
        return tvSeriesDao.getTvSeriesWatchList()
            .map { it.map { it.tvSeries } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}