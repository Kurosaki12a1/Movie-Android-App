package com.kuro.movie.data.data_source.remote

import com.kuro.movie.domain.response.person_detail.PersonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonAPI {

    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = "combined_credits"
    ): Response<PersonDetailResponse>
}