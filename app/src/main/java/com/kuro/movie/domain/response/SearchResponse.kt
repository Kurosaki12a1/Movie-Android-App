package com.kuro.movie.domain.response

import com.google.gson.annotations.SerializedName
import com.kuro.movie.data.model.Movie
import com.kuro.movie.data.model.TvSeries
import com.kuro.movie.domain.model.MediaType
import com.kuro.movie.domain.model.MultiSearch
import com.kuro.movie.domain.model.PersonSearch
import com.kuro.movie.extension.orZero
import com.kuro.movie.util.Utils

data class SearchResponse(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    val gender: Int?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("known_for") val knownForDto: List<KnownForResponse?>?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("media_type") val mediaType: String?,
    val name: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    val genreByOneForMovie: String = "",
    val genreByOneForTv: String = "",
    val voteCountByString: String = ""
)

fun SearchResponse.multiSearch(): MultiSearch? {
    return when (mediaType!!) {
        MediaType.MOVIE.value -> {
            MultiSearch.MovieItem(movie = this.toMovie())
        }

        MediaType.TV_SERIES.value -> {
            MultiSearch.TvSeriesItem(tvSeries = this.toTvSeries())
        }

        MediaType.PERSON.value -> {
            MultiSearch.PersonItem(person = this.toPersonSearch())
        }

        else -> null
    }
}

fun SearchResponse.toMovie(): Movie {
    return Movie(
        id = id.orZero(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount),
        genreIds = genreIds.orEmpty()
    )
}

fun SearchResponse.toTvSeries(): TvSeries {
    return TvSeries(
        id = id.orZero(),
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        firstAirDate = Utils.convertToYearFromDate(firstAirDate),
        genreIds = genreIds.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = Utils.formatVoteCount(voteCount)
    )
}

fun SearchResponse.toPersonSearch(): PersonSearch {
    return PersonSearch(
        id = id.orZero(),
        name = name.orEmpty(),
        profilePath = posterPath,
        knownForDepartment = knownForDepartment.orEmpty(),
        knownFor = knownForDto!!.toKnownForSearch()
    )
}
