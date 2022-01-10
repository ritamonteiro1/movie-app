package com.example.tokenlab.di


import com.example.tokenlab.presentation.movie.MovieListActivity
import com.example.tokenlab.presentation.movie_details.MovieDetailsActivity
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun injectInMovieListActivity(movieListActivity: MovieListActivity)
    fun injectInMovieDetailsActivity(movieDetailsActivity: MovieDetailsActivity)
}