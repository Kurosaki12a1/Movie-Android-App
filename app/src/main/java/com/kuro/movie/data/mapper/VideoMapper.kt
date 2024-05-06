package com.kuro.movie.data.mapper

import com.kuro.movie.domain.model.VideoResult
import com.kuro.movie.domain.model.Videos
import com.kuro.movie.domain.response.detail.video.VideoResultResponse
import com.kuro.movie.domain.response.detail.video.VideosResponse
import com.kuro.movie.extension.orZero

fun VideosResponse.toVideo(): Videos {
    return Videos(
        id = id.orZero(),
        result = results?.map { it.toVideoResult() }.orEmpty()
    )
}

fun VideoResultResponse.toVideoResult(): VideoResult {
    return VideoResult(
        id = id.orEmpty(),
        key = key.orEmpty(),
        site = site.orEmpty(),
        name = name.orEmpty(),
        type = type.orEmpty(),
        publishedAt = publishedAt.orEmpty()
    )
}

fun Videos.filterTrailerOrTeaserSortedByDescending() = this.copy(
    result = this.result.filter { it.isTypeTrailer() || it.isTypeTeaser() }
        .sortedByDescending { it.isTypeTrailer() }
)