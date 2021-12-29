package com.example.tokenlab.data.mapper

import com.example.tokenlab.data.remote.model.movie.MovieResponse
import com.example.tokenlab.data.remote.model.movie_details.details.MovieDetailsResponse
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import com.example.tokenlab.domain.model.movie_details.production_company.ProductionCompany
import com.example.tokenlab.domain.model.movie_details.production_country.ProductionCountry
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage

fun MovieDetailsResponse.convertToMovieDetailsModel(): MovieDetails {
    return MovieDetails(
        this.title,
        this.voteAverage,
        this.voteCount,
        this.releaseDate,
        this.posterUrl,
        this.originalLanguage,
        this.originalTitle,
        this.tagline,
        this.spokenLanguages.map {
            SpokenLanguage(it.name)
        },
        this.productionCountries.map {
            ProductionCountry(it.name)
        },
        this.productionCompanies.map {
            ProductionCompany(
                it.name,
                it.originCountry,
                it.logoUrl
            )
        },
        this.genres.map {
            it
        },
    )
}

fun List<MovieResponse>.convertToMovieListModel(): List<Movie> {
    return this.map { movieListResponse ->
        Movie(
            movieListResponse.id,
            movieListResponse.voteAverage,
            movieListResponse.title,
            movieListResponse.imageUrl,
            movieListResponse.releaseDate
        )
    }
}