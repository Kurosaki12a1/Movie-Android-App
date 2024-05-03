package com.kuro.movie.domain.usecase.movie

import androidx.paging.PagingData
import com.kuro.movie.data.model.Movie
import com.kuro.movie.domain.model.FilterBottomState
import com.kuro.movie.domain.repository.ExploreRepository
import com.kuro.movie.util.combineMovieAndGenreMapOneGenre
import io.reactivex.Observable
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {

    suspend operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Observable<PagingData<Movie>> {
        return combineMovieAndGenreMapOneGenre(
            listGenre = getMovieGenreListUseCase(language = language),
            moviePagingDataObservable = exploreRepository.discoverMovie(
                language = language,
                filterBottomState = filterBottomState
            )
        )
    }
}