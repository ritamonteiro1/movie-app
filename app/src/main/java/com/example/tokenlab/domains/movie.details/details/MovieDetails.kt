package com.example.tokenlab.domains.movie.details.details


data class MovieDetails(
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val imageUrl: String,
    val originalLanguage: String,
    val originalTitle: String,
    val tagline: String,
    val belongsToCollection: String
)
