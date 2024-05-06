package com.kuro.movie.data.repository.detail

import com.kuro.movie.data.mapper.toMovie
import com.kuro.movie.data.mapper.toMovieDetail
import com.kuro.movie.data.mapper.toVideo
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.repository.data_source.remote.MovieDetailRemoteRepository
import com.kuro.movie.domain.repository.detail.MovieDetailRepository
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Resource
import com.kuro.movie.util.safeApiCallReturnResource
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val repository: MovieDetailRemoteRepository
) : MovieDetailRepository {
    override suspend fun getMovieDetail(
        movieId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<MovieDetail> {
        return safeApiCallReturnResource {
            repository.getMovieDetail(movieId, language).toMovieDetail(Constants.DEFAULT_LANGUAGE)
        }
    }

    override suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int
    ): List<Movie> {
        return repository.getRecommendationsForMovie(
            movieId,
            language,
            page
        ).results.map { it.toMovie() }
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): Resource<Videos> {
        return safeApiCallReturnResource {
            repository.getMovieVideos(movieId, language).toVideo()
        }
    }

}