package com.example.tokenlab.data.remote.model.movie_details.details

import com.example.tokenlab.data.remote.model.movie_details.production_company.ProductionCompanyResponse
import com.example.tokenlab.data.remote.model.movie_details.production_country.ProductionCountryResponse
import com.example.tokenlab.data.remote.model.movie_details.spoken_language.SpokenLanguageResponse
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_url") val posterUrl: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val tagline: String,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageResponse>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryResponse>,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyResponse>,
    val genres: List<String>,
)
