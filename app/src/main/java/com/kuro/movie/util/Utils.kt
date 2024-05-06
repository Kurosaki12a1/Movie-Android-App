package com.kuro.movie.util

import com.kuro.movie.data.model.Genre
import com.kuro.movie.util.Constants.HOUR_KEY
import com.kuro.movie.util.Constants.MINUTES_KEY

object Utils {
    fun convertToYearFromDate(releaseDate: String?): String {
        if (releaseDate == null) return ""
        return releaseDate.split("-").first()
    }

    fun getGenresBySeparatedByComma(genreList: List<Genre>?): String {
        if (genreList == null) return ""
        return genreList.joinToString(", ") { genre -> genre.name }
    }

    fun formatVoteCount(voteCount: Int?): String {
        if (voteCount == null) return ""
        if (voteCount < 1000) return voteCount.toString()
        val voteAverageInThousand = voteCount / 1000
        return "${voteAverageInThousand}k"
    }

    fun calculateRatingBarValue(voteAverage: Double?): Float {
        if (voteAverage == null) return 0f
        return ((voteAverage * 5) / 10).toFloat()
    }

    fun convertRuntimeAsHourAndMinutes(runtime: Int?): Map<String, String> {
        runtime?.let {
            val hour = runtime / 60
            val minutes = (runtime % 60)
            return mapOf(
                HOUR_KEY to hour.toString(),
                MINUTES_KEY to minutes.toString()
            )
        } ?: return emptyMap()
    }

    fun convertTvSeriesReleaseDateBetweenFirstAndLastDate(
        firstAirDate: String?,
        lastAirDate: String?,
        status: String?
    ): String {
        if (firstAirDate == null || lastAirDate == null || status == null) return ""
        val firstAirDateValue = convertToYearFromDate(firstAirDate)
        return if (status == Constants.TV_SERIES_STATUS_ENDED) {
            val lastAirDateValue = convertToYearFromDate(lastAirDate)
            "${firstAirDateValue}-${lastAirDateValue}"
        } else {
            "$firstAirDateValue-"
        }
    }
}
