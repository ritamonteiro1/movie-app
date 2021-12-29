package com.example.tokenlab.data.api

import com.example.tokenlab.data.remote.model.movie.MovieResponse
import com.example.tokenlab.data.remote.model.movie_details.details.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDataService {
    @GET("movies")
    suspend fun recoverMovieList(): Response<List<MovieResponse>>

    @GET("movies/{id}")
    suspend fun recoverMovieDetails(
        @Path("id") id: Int
    ): Response<MovieDetailsResponse>
}