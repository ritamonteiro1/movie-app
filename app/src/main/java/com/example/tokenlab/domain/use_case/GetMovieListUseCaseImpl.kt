package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.repository.MovieRepository
import com.example.tokenlab.domain.model.movie.Movie

class GetMovieListUseCaseImpl(private val movieRepository: MovieRepository): GetMovieListUseCase {
    override suspend fun getMovieList(): List<Movie> {
        return movieRepository.fetchMovieList()
    }
}