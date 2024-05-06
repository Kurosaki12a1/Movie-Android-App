package com.kuro.movie.domain.response.detail.tv

import com.google.gson.annotations.SerializedName
import com.kuro.movie.data.model.Genre
import com.kuro.movie.domain.response.detail.CreditResponse
import com.kuro.movie.domain.response.detail.ProductionCompany
import com.kuro.movie.domain.response.detail.ProductionCountry
import com.kuro.movie.domain.response.detail.SpokenLanguage
import com.kuro.movie.domain.response.detail.WatchProviderResponse

data class TvDetailResponse(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("created_by") val createdBy: List<CreatedByResponse>?,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("in_production") val inProduction: Boolean?,
    val languages: List<String>?,
    @SerializedName("last_air_date") val lastAirDate: String?,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAirResponse?,
    val name: String?,
    val networks: List<NetworkResponse>?,
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: Any?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    val seasonDto: List<SeasonResponse>?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    val credits: CreditResponse?,
    @SerializedName("watch/providers") val watchProviders: WatchProviderResponse?
)