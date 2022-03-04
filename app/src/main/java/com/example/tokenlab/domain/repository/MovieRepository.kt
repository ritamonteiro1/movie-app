package com.example.tokenlab.domain.repository

import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

interface MovieRepository {
    suspend fun fetchMovieList(): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}