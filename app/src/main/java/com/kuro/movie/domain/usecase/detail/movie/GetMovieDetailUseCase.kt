package com.kuro.movie.domain.usecase.detail.movie

import com.kuro.movie.domain.model.MovieDetail
import com.kuro.movie.domain.repository.detail.MovieDetailRepository
import com.kuro.movie.domain.usecase.movie.GetFavoriteMovieIdsUseCase
import com.kuro.movie.domain.usecase.movie.GetMovieWatchListItemIdsUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Resource
import com.kuro.movie.util.handleResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    private val getWatchListMovieIdsUseCase: GetMovieWatchListItemIdsUseCase
) {
    suspend operator fun invoke(
        language: String,
        movieId: Int
    ): Resource<MovieDetail> {
        return withContext(Dispatchers.IO) {
            handleResource(
                resourceSupplier = {
                    movieDetailRepository.getMovieDetail(
                        language = language,
                        movieId = movieId,
                        countryIsoCode = Constants.DEFAULT_LANGUAGE
                    )
                },
                mapper = {
                    it.copy(
                        isFavorite = getFavoriteMovieIdsUseCase().contains(it.id),
                        isWatchList = getWatchListMovieIdsUseCase().contains(it.id)
                    )
                }
            )
        }
    }
}
