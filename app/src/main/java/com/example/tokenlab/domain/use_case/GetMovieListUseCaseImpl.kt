package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.data_repository.MovieDataRepository
import com.example.tokenlab.domain.model.movie.Movie

class GetMovieListUseCaseImpl(private val movieDataRepository: MovieDataRepository): GetMovieListUseCase {
    override suspend fun getMovieList(): List<Movie> {
        return movieDataRepository.fetchMovieList()
    }
}