package com.kuro.movie.util

import com.kuro.movie.data.model.Genre

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
}