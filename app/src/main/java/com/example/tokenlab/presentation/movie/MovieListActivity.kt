package com.example.tokenlab.presentation.movie

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenlab.R
import com.example.tokenlab.constants.Constants
import com.example.tokenlab.databinding.ActivityMovieListBinding
import com.example.tokenlab.di.ApplicationComponent
import com.example.tokenlab.di.DaggerApplicationComponent
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.extensions.createLoadingDialog
import com.example.tokenlab.extensions.showErrorDialogWithAction
import com.example.tokenlab.presentation.movie_details.MovieDetailsActivity
import javax.inject.Inject

class MovieListActivity : AppCompatActivity() {
    private val component: ApplicationComponent? by lazy {
        DaggerApplicationComponent.builder().build()
    }
    @Inject
    lateinit var viewModel: MovieListViewModel
    private lateinit var binding: ActivityMovieListBinding
    private val loadingDialog: Dialog by lazy { createLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        component?.injectInMovieListActivity(this)
        setupObservers()
        setupToolBar()
        getMovieList()
    }

    private fun setupObservers() {
        setupMovieListObserver()
        setupLoadingObserver()
        setupNetworkErrorObserver()
        setupGenericErrorObserver()
    }

    private fun setupLoadingObserver() {
        viewModel.loading.observe(this, { loading ->
            if (loading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun setupNetworkErrorObserver() {
        viewModel.networkError.observe(this, {
            binding.movieListRecyclerView.visibility = View.GONE
            this@MovieListActivity.showErrorDialogWithAction(
                getString(R.string.network_error)
            ) { _, _ -> getMovieList() }
        })
    }

    private fun setupGenericErrorObserver() {
        viewModel.genericError.observe(this, {
            binding.movieListRecyclerView.visibility = View.GONE
            this@MovieListActivity.showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> getMovieList() }
        })
    }

    private fun setupMovieListObserver() {
        viewModel.movieList.observe(this, { movieList ->
            if (movieList.isEmpty()) {
                treatMovieListEmpty()
            } else {
                binding.movieListRecyclerView.visibility = View.VISIBLE
                createMovieListAdapter(movieList)
            }
        })
    }

    private fun getMovieList() {
        viewModel.getMovieList()
    }

    private fun createMovieListAdapter(movieList: List<Movie>) {
        val movieListAdapter = MovieListAdapter { movieId ->
            val intent =
                Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
            intent.putExtra(Constants.ID_MOVIE, movieId)
            startActivity(intent)
        }
        setupAdapter(movieListAdapter)
        movieListAdapter.setData(movieList)
    }

    private fun setupAdapter(movieListAdapter: MovieListAdapter) {
        binding.movieListRecyclerView.adapter = movieListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.movieListRecyclerView.layoutManager = layoutManager

    }

    private fun treatMovieListEmpty() {
        binding.movieListRecyclerView.visibility = View.GONE
        this.showErrorDialogWithAction(
            getString(R.string.occurred_error)
        ) { _, _ -> getMovieList() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.movieListToolBar)
        supportActionBar?.title = getString(R.string.main_tool_bar_title_text)
    }
}