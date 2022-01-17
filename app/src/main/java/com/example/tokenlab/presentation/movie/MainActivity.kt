package com.example.tokenlab.presentation.movie

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val MOVIE_LIST_FRAGMENT_TAG: String = "movieListFragment"
        private const val FAVORITE_MOVIE_LIST_FRAGMENT_TAG: String = "favoriteMovieListFragment"
    }

    private var activeFragmentTag: String? = MOVIE_LIST_FRAGMENT_TAG

    private val movieListFragment: MovieListFragmentContainer by lazy {
        supportFragmentManager.findFragmentByTag(MOVIE_LIST_FRAGMENT_TAG) as? MovieListFragmentContainer
            ?: MovieListFragmentContainer.newInstance()
    }

    private val favoriteMovieListFragment: FavoriteMovieListFragmentContainer by lazy {
        supportFragmentManager.findFragmentByTag(FAVORITE_MOVIE_LIST_FRAGMENT_TAG) as? FavoriteMovieListFragmentContainer
            ?: FavoriteMovieListFragmentContainer.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavActions()

        supportFragmentManager.beginTransaction().hide(favoriteMovieListFragment)
            .show(movieListFragment).commit()
        activeFragmentTag = movieListFragment.tag
    }

    private fun setupNavActions() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFlowContainer, movieListFragment, MOVIE_LIST_FRAGMENT_TAG)
            .add(
                R.id.mainFlowContainer,
                favoriteMovieListFragment,
                FAVORITE_MOVIE_LIST_FRAGMENT_TAG
            )
            .commitNow()
        binding.mainBottomNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(
                it
            )
        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.movieList -> {
                supportFragmentManager.beginTransaction()
                    .hide(favoriteMovieListFragment)
                    .show(movieListFragment).commit()
                activeFragmentTag = movieListFragment.tag
                true
            }
            R.id.favoriteMovieList -> {
                supportFragmentManager.beginTransaction().hide(movieListFragment)
                    .show(favoriteMovieListFragment).commit()
                activeFragmentTag = favoriteMovieListFragment.tag
                true
            }
            else -> {
                false
            }
        }
    }
}