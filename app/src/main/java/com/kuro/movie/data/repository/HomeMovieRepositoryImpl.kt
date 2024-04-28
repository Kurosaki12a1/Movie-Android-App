package com.kuro.movie.data.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import com.kuro.movie.data.data_source.remote.HomeAPI
import com.kuro.movie.data.mapper.toMovie
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.HomeMovieRepository
import javax.inject.Inject

class HomeMovieRepositoryImpl @Inject constructor(
    private val homeAPI: HomeAPI
) : HomeMovieRepository {

    override suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): Observable<Movie> {
        return homeAPI.getNowPlayingMovie(page, region, language).map { response ->
            response.toMovie()
        }
    }

    override suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): Single<Movie> {
        return homeAPI.getPopularMovies(page, region, language).map { response ->
            response.toMovie()
        }
    }

    override suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): Single<Movie> {
        return homeAPI.getTopRatedMovies(page, region, language).map { response ->
            response.toMovie()
        }
    }
}