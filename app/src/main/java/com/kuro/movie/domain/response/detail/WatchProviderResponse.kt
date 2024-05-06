package com.kuro.movie.domain.response.detail

import com.google.gson.annotations.SerializedName

class WatchProviderResponse(
    val results: WatchProviderRegionResponse?
)

data class WatchProviderItemResponse(
    val link: String,
    @SerializedName("flatrate") val flatRate: List<WatchProviderItemDetailResponse>?,
    val rent: List<WatchProviderItemDetailResponse>?,
    val buy: List<WatchProviderItemDetailResponse>?,
    val free: List<WatchProviderItemDetailResponse>?,
)

data class WatchProviderItemDetailResponse(
    @SerializedName("display_priority") val displayPriority: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
)

data class WatchProviderRegionResponse(
    @SerializedName("TR") val tr: WatchProviderItemResponse?,
    @SerializedName("ES") val es: WatchProviderItemResponse?,
    @SerializedName("DE") val de: WatchProviderItemResponse?,
    @SerializedName("FR") val fr: WatchProviderItemResponse?,
    @SerializedName("US") val us: WatchProviderItemResponse?,
)

enum class Country(val isoCode: String) {
    US("us"),
    TR("TR"),
    ES("ES"),
    DE("DE"),
    FR("FR")
}