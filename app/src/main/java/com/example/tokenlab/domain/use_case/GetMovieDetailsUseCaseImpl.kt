package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.repository.MovieRepository
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

class GetMovieDetailsUseCaseImpl(private val movieRepository: MovieRepository) :
    GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieRepository.fetchMovieDetails(movieId)
    }
}