package com.example.tokenlab.domain.use_case

import com.example.tokenlab.domain.model.movie_details.details.MovieDetails

interface GetMovieDetailsUseCase{
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}