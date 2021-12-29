package com.example.tokenlab.data.remote.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    val title: String,
    @SerializedName("poster_url") val imageUrl: String,
    @SerializedName("release_date") val releaseDate: String
)