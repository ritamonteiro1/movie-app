package com.example.tokenlab.data.mapper

import com.example.tokenlab.constants.Constants
import com.example.tokenlab.data.remote.model.movie.MovieResponse
import com.example.tokenlab.data.remote.model.movie_details.details.MovieDetailsResponse
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import com.example.tokenlab.domain.model.movie_details.production_company.ProductionCompany
import com.example.tokenlab.domain.model.movie_details.production_country.ProductionCountry
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage
import com.example.tokenlab.extensions.convertIfIsNullOrBlank

fun MovieDetailsResponse.convertToMovieDetailsModel(): MovieDetails {
    return MovieDetails(
        this.title.convertIfIsNullOrBlank(),
        this.voteAverage ?: Constants.NULL_DOUBLE_RESPONSE,
        this.voteCount ?: Constants.NULL_INT_RESPONSE,
        this.releaseDate.orEmpty(),
        this.posterUrl.orEmpty(),
        this.originalLanguage.convertIfIsNullOrBlank(),
        this.originalTitle.convertIfIsNullOrBlank(),
        this.tagline.convertIfIsNullOrBlank(),
        this.spokenLanguages?.map {
            SpokenLanguage(it.name.convertIfIsNullOrBlank())
        } ?: emptyList(),
        this.productionCountries?.map {
            ProductionCountry(it.name.convertIfIsNullOrBlank())
        } ?: emptyList(),
        this.productionCompanies?.map {
            ProductionCompany(
                it.name.convertIfIsNullOrBlank(),
                it.originCountry.convertIfIsNullOrBlank(),
                it.logoUrl.orEmpty()
            )
        } ?: emptyList(),
        this.genres?.map {
            it
        } ?: emptyList(),
    )
}

fun List<MovieResponse>.convertToMovieListModel(): List<Movie> {
    return this.map { movieListResponse ->
        Movie(
            movieListResponse.id ?: Constants.NULL_INT_RESPONSE,
            movieListResponse.voteAverage ?: Constants.NULL_DOUBLE_RESPONSE,
            movieListResponse.title.convertIfIsNullOrBlank(),
            movieListResponse.imageUrl.orEmpty(),
            movieListResponse.releaseDate.orEmpty()
        )
    }
}