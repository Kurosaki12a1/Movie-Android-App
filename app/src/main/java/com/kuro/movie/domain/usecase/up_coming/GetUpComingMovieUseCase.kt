@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kuro.movie.domain.usecase.up_coming

import android.os.Build
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
import com.kuro.movie.domain.model.UpComingMovie
import com.kuro.movie.domain.repository.UpComingRepository
import com.kuro.movie.domain.usecase.movie.GetMovieGenreListUseCase
import com.kuro.movie.util.DateFormatUtils
import com.kuro.movie.util.GenreDomainUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Calendar
import javax.inject.Inject

class GetUpcomingMovieUseCase @Inject constructor(
    private val repository: UpComingRepository,
    private val movieGenreListUseCase: GetMovieGenreListUseCase,
) {
    private val calendar = Calendar.getInstance()
    suspend operator fun invoke(
        language: String,
        scope: CoroutineScope
    ): Observable<PagingData<UpComingMovie>> {
        val upcomingRemind = repository.getAllUpcomingRemind()
        return repository.getUpComingMovies(language).cachedIn(scope)
            .map { pagingData ->
                pagingData.map { upComingMovie ->
                    val genresByComma = GenreDomainUtils.getGenresBySeparatedByComma(
                        genres = movieGenreListUseCase(language),
                        genreIds = upComingMovie.movie.genreIds
                    )
                    upComingMovie.copy(
                        movie = upComingMovie.movie.copy(
                            genresBySeparatedByComma = genresByComma
                        ),
                        isAddedToRemind = upcomingRemind.any { it.movieId == upComingMovie.movie.id }
                    )
                }.filter { upComingMovie ->
                    upComingMovie.movie.fullReleaseDate?.let { releaseDate ->
                        isAfterReleaseDate(releaseDate)
                    } ?: false
                }
            }.subscribeOn(Schedulers.io())
    }


    private fun isAfterReleaseDate(releaseDate: String): Boolean {
        val date = DateFormatUtils.convertToDateFromReleaseDate(releaseDate)

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date.year >= currentYear &&
                    date.month.value >= currentMonth &&
                    date.dayOfMonth >= currentDay
        } else {
            date.year >= currentYear &&
                    date.month.ordinal + 1 >= currentMonth &&
                    date.dayOfMonth >= currentDay
        }
    }

}