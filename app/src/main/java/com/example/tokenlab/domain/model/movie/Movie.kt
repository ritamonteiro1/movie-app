package com.example.tokenlab.domain.model.movie


data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val imageUrl: String,
    val releaseDate: String
)