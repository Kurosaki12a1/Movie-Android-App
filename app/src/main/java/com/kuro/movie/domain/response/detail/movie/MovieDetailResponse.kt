package com.kuro.movie.domain.response.detail.movie

import com.google.gson.annotations.SerializedName
import com.kuro.movie.data.model.Genre
import com.kuro.movie.domain.response.detail.CreditResponse
import com.kuro.movie.domain.response.detail.ProductionCompany
import com.kuro.movie.domain.response.detail.ProductionCountry
import com.kuro.movie.domain.response.detail.SpokenLanguage
import com.kuro.movie.domain.response.detail.WatchProviderResponse

data class MovieDetailResponse(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: Any?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Any?,
    val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    val credits: CreditResponse?,
    @SerializedName("watch/providers") val watchProviders: WatchProviderResponse?
)