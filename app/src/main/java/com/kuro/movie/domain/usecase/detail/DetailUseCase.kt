package com.kuro.movie.domain.usecase.detail

import com.kuro.movie.domain.usecase.detail.movie.GetMovieDetailUseCase
import com.kuro.movie.domain.usecase.detail.movie.GetMovieRecommendationUseCase
import com.kuro.movie.domain.usecase.detail.movie.GetMovieVideosUseCase
import com.kuro.movie.domain.usecase.detail.tv.GetTvDetailUseCase
import com.kuro.movie.domain.usecase.detail.tv.GetTvRecommendationUseCase
import com.kuro.movie.domain.usecase.detail.tv.GetTvVideosUseCase

data class DetailUseCase(
    val movieDetailUseCase: GetMovieDetailUseCase,
    val tvDetailUseCase: GetTvDetailUseCase,
    val getMovieRecommendationUseCase: GetMovieRecommendationUseCase,
    val getTvRecommendationUseCase: GetTvRecommendationUseCase,
    val getMovieVideosUseCase: GetMovieVideosUseCase,
    val getTvVideosUseCase: GetTvVideosUseCase
)