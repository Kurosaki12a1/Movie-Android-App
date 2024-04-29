package com.kuro.movie.data.data_source.remote

import com.kuro.movie.domain.response.GenreListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreAPI {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): Observable<GenreListResponse>

    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): Observable<GenreListResponse>
}