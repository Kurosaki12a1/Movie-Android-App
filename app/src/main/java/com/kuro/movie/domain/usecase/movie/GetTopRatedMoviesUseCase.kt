package com.kuro.movie.domain.usecase.movie

import androidx.paging.PagingData
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.repository.HomeMovieRepository
import com.kuro.movie.util.Constants.DEFAULT_LANGUAGE
import com.kuro.movie.util.Constants.DEFAULT_REGION
import com.kuro.movie.util.combineMovieAndGenreMapOneGenre
import io.reactivex.Observable
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val homeMovieRepository: HomeMovieRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    suspend operator fun invoke(
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Observable<PagingData<Movie>> {
        return combineMovieAndGenreMapOneGenre(
            movieGenreResourceObservable = getMovieGenreListUseCase(language = language),
            moviePagingDataObservable = homeMovieRepository.getTopRatedMovies(
                language = language,
                region = region
            )
        )
    }
}