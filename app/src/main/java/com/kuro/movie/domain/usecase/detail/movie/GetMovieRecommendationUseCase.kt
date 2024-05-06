package com.kuro.movie.domain.usecase.detail.movie

import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.detail.MovieDetailRepository
import com.kuro.movie.domain.usecase.movie.GetMovieGenreListUseCase
import com.kuro.movie.util.GenreDomainUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieRecommendationUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    suspend operator fun invoke(
        language: String,
        movieId: Int
    ): List<Movie> {
        return withContext(Dispatchers.IO) {
            val movies = async {
                movieDetailRepository.getRecommendationsForMovie(
                    movieId = movieId,
                    language = language
                )
            }

            val genres = async { getMovieGenreListUseCase(language) }

            movies.await().map { movie ->
                movie.copy(
                    genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres.await(),
                        genreIds = movie.genreIds
                    )
                )
            }
        }
    }
}