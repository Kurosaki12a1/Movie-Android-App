package com.kuro.movie.domain.model

import com.kuro.movie.util.Constants.TYPE_TEASER
import com.kuro.movie.util.Constants.TYPE_TRAILER

data class VideoResult(
    val id: String,
    val key: String,
    val site: String,
    val name: String,
    val type: String,
    val publishedAt: String
) {
    fun isTypeTrailer() = this.type == TYPE_TRAILER

    fun isTypeTeaser() = this.type == TYPE_TEASER
}

data class Videos(
    val id: Int,
    val result: List<VideoResult>
)