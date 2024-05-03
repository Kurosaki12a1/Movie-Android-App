package com.kuro.movie.domain.response

import com.google.gson.annotations.SerializedName
import com.kuro.movie.domain.model.KnownForSearch
import com.kuro.movie.extension.orZero

data class KnownForResponse(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("media_type") val mediaType: String?,
    val name: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)

fun List<KnownForResponse?>.toKnownForSearch(): List<KnownForSearch> {
    return map {
        KnownForSearch(
            id = it?.id.orZero(),
            overview = it?.overview.orEmpty(),
            firstAirDate = it?.firstAirDate.orEmpty(),
            genreIds = it?.genreIds.orEmpty(),
            mediaType = it?.mediaType.orEmpty(),
            name = it?.name.orEmpty(),
            originalName = it?.originalName.orEmpty(),
            originalTitle = it?.originalTitle.orEmpty(),
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate.orEmpty(),
            title = it?.title.orEmpty(),
            voteAverage = it?.voteAverage.orZero(),
            voteCount = it?.voteCount.orZero()
        )
    }
}