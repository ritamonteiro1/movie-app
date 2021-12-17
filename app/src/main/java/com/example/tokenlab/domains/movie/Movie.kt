package com.example.tokenlab.domains.movie


data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val imageUrl: String,
    val releaseDate: String
)