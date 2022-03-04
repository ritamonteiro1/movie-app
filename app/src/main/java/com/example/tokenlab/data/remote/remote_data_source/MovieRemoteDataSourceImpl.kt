package com.example.tokenlab.data.remote.remote_data_source

import android.accounts.NetworkErrorException
import com.example.tokenlab.data.api.MovieDataService
import com.example.tokenlab.data.remote.model.movie.MovieResponse
import com.example.tokenlab.data.remote.model.movie_details.details.MovieDetailsResponse
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MovieRemoteDataSourceImpl(private val movieDataService: MovieDataService) :
    MovieRemoteDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse? {
        try {
            val movieDetailsResponse = movieDataService.fetchMovieDetails(movieId)
            return movieDetailsResponse.body()
        } catch (e: UnknownHostException) {
            throw NetworkErrorException()
        } catch (e: SocketTimeoutException) {
            throw NetworkErrorException()
        }

    }

    override suspend fun fetchMovieList(): List<MovieResponse>? {
        try {
            val movieListResponse = movieDataService.fetchMovieList()
            return movieListResponse.body()
        } catch (e: UnknownHostException) {
            throw NetworkErrorException()
        } catch (e: SocketTimeoutException) {
            throw NetworkErrorException()
        }
    }
}