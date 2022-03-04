package com.example.tokenlab.data.repository

import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSource
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import com.example.tokenlab.domain.repository.MovieRepository

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override suspend fun fetchMovieList(): List<Movie> {
        return movieRemoteDataSource.fetchMovieList()
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieRemoteDataSource.fetchMovieDetails(movieId)
    }
}