package com.example.tokenlab.data.remote.remote_data_source

import com.example.tokenlab.data.api.MovieDataService
import com.example.tokenlab.data.mapper.convertToMovieDetailsModel
import com.example.tokenlab.data.mapper.convertToMovieListModel
import com.example.tokenlab.domain.exception.GenericErrorException
import com.example.tokenlab.domain.exception.NetworkException
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MovieRemoteDataSourceImpl(private val movieDataService: MovieDataService) :
    MovieRemoteDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        try {
            val response = movieDataService.fetchMovieDetails(movieId)
            return response.convertToMovieDetailsModel()
        } catch (e: UnknownHostException) {
            throw NetworkException()
        } catch (e: SocketTimeoutException) {
            throw NetworkException()
        } catch (e: Exception) {
            throw GenericErrorException()
        }
    }

    override suspend fun fetchMovieList(): List<Movie> {
        try {
            val response = movieDataService.fetchMovieList()
            return response.convertToMovieListModel()
        } catch (e: UnknownHostException) {
            throw NetworkException()
        } catch (e: SocketTimeoutException) {
            throw NetworkException()
        } catch (e: Exception) {
            throw GenericErrorException()
        }
    }
}