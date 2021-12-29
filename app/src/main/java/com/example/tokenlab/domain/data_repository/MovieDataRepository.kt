package com.example.tokenlab.domain.data_repository

import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

interface MovieDataRepository {
    suspend fun fetchMovieList(): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}