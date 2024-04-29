package com.kuro.movie.data.repository

import androidx.paging.PagingData
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.repository.HomeTvRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeTvSeriesRemoteRepository
import com.kuro.movie.util.getPagingTvSeries
import io.reactivex.Observable
import javax.inject.Inject

class HomeTvRepositoryImpl @Inject constructor(
    private val dataSource: HomeTvSeriesRemoteRepository
) : HomeTvRepository {
    override fun getPopularTvs(language: String): Observable<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            dataSource.getPopularTvs(
                page = page,
                language = language
            )
        }
    }

    override fun getTopRatedTvs(language: String): Observable<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            dataSource.getTopRatedTvs(
                page = page,
                language = language
            )
        }
    }
}