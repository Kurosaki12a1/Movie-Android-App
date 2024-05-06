package com.kuro.movie.domain.usecase.detail.movie

import com.kuro.movie.data.mapper.filterTrailerOrTeaserSortedByDescending
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.repository.detail.MovieDetailRepository
import com.kuro.movie.util.Resource
import com.kuro.movie.util.handleResource
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {

    suspend operator fun invoke(movieId: Int, language: String): Resource<Videos> {
        return handleResource(
            resourceSupplier = {
                movieDetailRepository.getMovieVideos(
                    movieId = movieId,
                    language = language.lowercase()
                )
            },
            mapper = { videos ->
                videos.filterTrailerOrTeaserSortedByDescending()
            }
        )
    }
}