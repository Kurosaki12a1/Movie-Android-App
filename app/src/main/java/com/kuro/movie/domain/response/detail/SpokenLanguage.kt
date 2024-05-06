package com.kuro.movie.domain.response.detail

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_3166_1") val iso: String?,
    val name: String?
)