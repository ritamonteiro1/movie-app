package com.example.tokenlab.data.remote.remote_data_source

import android.accounts.NetworkErrorException
import com.example.tokenlab.data.api.MovieDataService
import com.example.tokenlab.data.mapper.convertToMovieDetailsModel
import com.example.tokenlab.data.mapper.convertToMovieListModel
import com.example.tokenlab.domain.exception.GenericErrorException
import com.example.tokenlab.domain.exception.NullResponseException
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MovieRemoteDataSourceImpl(private val movieDataService: MovieDataService) :
    MovieRemoteDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        try {
            val response = movieDataService.fetchMovieDetails(movieId)
            val movieDetailsResponse = response.body()
            if (movieDetailsResponse != null) {
                return movieDetailsResponse.convertToMovieDetailsModel()
            } else {
                throw NullResponseException()
            }
        } catch (e: UnknownHostException) {
            throw NetworkErrorException()
        } catch (e: SocketTimeoutException) {
            throw NetworkErrorException()
        } catch (e: Exception) {
            throw GenericErrorException()
        }
    }

    override suspend fun fetchMovieList(): List<Movie> {
        try {
            val response = movieDataService.fetchMovieList()
            val movieListResponse = response.body()
            if (movieListResponse != null) {
                return movieListResponse.convertToMovieListModel()
            } else {
                throw NullResponseException()
            }
        } catch (e: UnknownHostException) {
            throw NetworkErrorException()
        } catch (e: SocketTimeoutException) {
            throw NetworkErrorException()
        } catch (e: Exception) {
            throw GenericErrorException()
        }
    }
}