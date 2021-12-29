package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.data_repository.MovieDataRepository
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

class GetMovieDetailsUseCaseImpl(private val movieDataRepository: MovieDataRepository) :
    GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieDataRepository.fetchMovieDetails(movieId)
    }
}