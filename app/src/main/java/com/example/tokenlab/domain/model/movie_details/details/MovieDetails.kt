package com.example.tokenlab.domain.model.movie_details.details

import com.example.tokenlab.domain.model.movie_details.production_company.ProductionCompany
import com.example.tokenlab.domain.model.movie_details.production_country.ProductionCountry
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage


data class MovieDetails(
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val imageUrl: String,
    val originalLanguage: String,
    val originalTitle: String,
    val tagline: String,
    val spokenLanguages: List<SpokenLanguage>,
    val productionCountries: List<ProductionCountry>,
    val productionCompanies: List<ProductionCompany>,
    val genres: List<String>,
)
