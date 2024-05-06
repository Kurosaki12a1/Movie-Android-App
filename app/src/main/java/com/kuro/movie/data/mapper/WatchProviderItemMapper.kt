package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.WatchProviderItem
import com.kuro.movie.domain.model.WatchProviderItemInfo
import com.kuro.movie.domain.response.detail.Country
import com.kuro.movie.domain.response.detail.WatchProviderItemDetailResponse
import com.kuro.movie.domain.response.detail.WatchProviderRegionResponse

fun WatchProviderRegionResponse.toWatchProviderItem(
    countryIsoCode: String
): WatchProviderItem {
    val providerRegionDto = when (countryIsoCode) {
        Country.US.isoCode -> this.us
        Country.DE.isoCode -> this.de
        Country.ES.isoCode -> this.es
        Country.FR.isoCode -> this.fr
        Country.TR.isoCode -> this.tr
        else -> this.us
    }

    return WatchProviderItem(
        stream = providerRegionDto?.flatRate.toWatchProviderItemDetail(),
        rent = providerRegionDto?.rent.toWatchProviderItemDetail(),
        buy = providerRegionDto?.rent.toWatchProviderItemDetail()
    )
}

fun List<WatchProviderItemDetailResponse>?.toWatchProviderItemDetail(): List<WatchProviderItemInfo> {
    return this?.sortedBy { it.displayPriority }?.take(2)?.map {
        WatchProviderItemInfo(
            logoPath = it.logoPath,
            providerName = it.providerName
        )
    } ?: emptyList()
}