package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.model.movie.Movie

interface GetMovieListUseCase {
   suspend fun getMovieList(): List<Movie>
}