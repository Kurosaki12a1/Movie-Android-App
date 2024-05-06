package com.kuro.movie.domain.repository.detail

import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.util.Resource

interface MovieDetailRepository {
    suspend fun getMovieDetail(
        movieId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<MovieDetail>

    suspend fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
        page: Int = 1
    ): List<Movie>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String
    ): Resource<Videos>
}