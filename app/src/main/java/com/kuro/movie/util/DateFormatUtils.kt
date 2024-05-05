package com.kuro.movie.util

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.time.Month
import java.util.Locale


object DateFormatUtils {

    fun convertDateFormat(inputDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())

        return try {
            val date = dateFormat.parse(inputDate)
            outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Parses a date string in the format "yyyy-MM-dd" and creates a [KuroDate] object.
     *
     * @param releaseDate The date string to be parsed.
     * @return A [KuroDate] object representing the parsed date.
     */
    fun convertToDateFromReleaseDate(releaseDate: String): KuroDate {

        val releaseDateSplit = releaseDate.split("-")
        val releaseYear = releaseDateSplit[0].toIntOrNull() ?: 0
        val releaseMonth = releaseDateSplit[1].toIntOrNull() ?: 0
        val releaseDay = releaseDateSplit[2].toIntOrNull() ?: 0

        return KuroDate(
            year = releaseYear,
            month = Month.of(releaseMonth),
            dayOfMonth = releaseDay
        )
    }
}

data class KuroDate(
    val year: Int,
    val month: Month,
    val dayOfMonth: Int
)