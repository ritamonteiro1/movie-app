package com.example.tokenlab.data.repository

import com.example.tokenlab.data.mapper.convertToMovieDetailsModel
import com.example.tokenlab.data.mapper.convertToMovieListModel
import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSource
import com.example.tokenlab.domain.repository.MovieRepository
import com.example.tokenlab.domain.exception.GenericErrorException
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override suspend fun fetchMovieList(): List<Movie> {
        val movieListResponse = movieRemoteDataSource.fetchMovieList()
        if (movieListResponse != null) {
            return movieListResponse.convertToMovieListModel()
        } else {
            throw GenericErrorException()
        }
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        val movieDetailsResponse = movieRemoteDataSource.fetchMovieDetails(movieId)
        if (movieDetailsResponse != null) {
            return movieDetailsResponse.convertToMovieDetailsModel()
        } else {
            throw GenericErrorException()
        }
    }
}