package com.example.tokenlab.data.remote.remote_data_source

import com.example.tokenlab.data.remote.model.movie.MovieResponse
import com.example.tokenlab.data.remote.model.movie_details.details.MovieDetailsResponse

interface MovieRemoteDataSource {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse?
    suspend fun fetchMovieList(): List<MovieResponse>?
}