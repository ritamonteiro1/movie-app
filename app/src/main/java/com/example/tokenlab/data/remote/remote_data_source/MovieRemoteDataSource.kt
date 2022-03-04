package com.example.tokenlab.data.remote.remote_data_source

import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

interface MovieRemoteDataSource {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
    suspend fun fetchMovieList(): List<Movie>
}