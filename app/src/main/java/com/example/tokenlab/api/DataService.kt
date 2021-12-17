package com.example.tokenlab.api

import com.example.tokenlab.domains.movie.MovieResponse
import com.example.tokenlab.domains.movie.details.details.MovieDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DataService {
    @GET("movies")
    fun recoverMovieList(): Call<List<MovieResponse>>

    @GET("movies/{id}")
    fun recoverMovieDetails(
        @Path("id") id: Int
    ): Call<MovieDetailsResponse>
}