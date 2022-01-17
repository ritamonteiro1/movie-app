package com.example.tokenlab.di


import com.example.tokenlab.presentation.movie.MovieListFragment
import com.example.tokenlab.presentation.movie_details.MovieDetailsActivity
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun injectInMovieListActivity(movieListFragment: MovieListFragment)
    fun injectInMovieDetailsActivity(movieDetailsActivity: MovieDetailsActivity)
}