package com.kuro.movie.data.repository

import androidx.paging.PagingData
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.domain.repository.data_source.remote.HomeMovieRemoteRepository
import com.kuro.movie.util.getPagingMovies
import io.reactivex.Observable
import javax.inject.Inject

class HomeMovieRepositoryImpl @Inject constructor(
    private val dataSource: HomeMovieRemoteRepository
) : HomeMovieRepository {
    override fun getNowPlayingMovies(
        language: String,
        region: String
    ): Observable<PagingData<Movie>> {
        return getPagingMovies { page ->
            dataSource.getNowPlayingMovies(
                page = page,
                language = language,
                region = region
            )
        }
    }

    override fun getPopularMovies(language: String, region: String): Observable<PagingData<Movie>> {
        return getPagingMovies { page ->
            dataSource.getPopularMovies(
                page = page,
                language = language,
                region = region
            )
        }
    }

    override fun getTopRatedMovies(
        language: String,
        region: String
    ): Observable<PagingData<Movie>> {
        return getPagingMovies { page ->
            dataSource.getTopRatedMovies(
                page = page,
                language = language,
                region = region
            )
        }
    }
}