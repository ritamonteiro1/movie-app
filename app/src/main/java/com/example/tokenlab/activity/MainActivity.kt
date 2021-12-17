package com.example.tokenlab.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenlab.R
import com.example.tokenlab.adapter.MovieListAdapter
import com.example.tokenlab.api.Api
import com.example.tokenlab.api.DataService
import com.example.tokenlab.click.listener.OnMovieButtonClickListener
import com.example.tokenlab.constants.Constants
import com.example.tokenlab.databinding.ActivityMainBinding
import com.example.tokenlab.domains.movie.Movie
import com.example.tokenlab.domains.movie.MovieResponse
import com.example.tokenlab.extensions.createLoadingDialog
import com.example.tokenlab.extensions.showErrorDialogWithAction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val loadingDialog: Dialog by lazy { createLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        loadingDialog.show()
        getMovieListFromApi()
    }

    private fun getMovieListFromApi() {
        val dataService: DataService = Api.setupRetrofit().create(DataService::class.java)
        val call: Call<List<MovieResponse>> = dataService.recoverMovieList()
        call.enqueue(object : Callback<List<MovieResponse>> {
            override fun onResponse(
                call: Call<List<MovieResponse>>,
                response: Response<List<MovieResponse>>
            ) {
                getMovieListFromApiOnResponse(response)
            }

            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                getMovieListFromApiOnFailure()
            }
        })
    }

    private fun getMovieListFromApiOnFailure() {
        loadingDialog.dismiss()
        binding.mainRecyclerView.visibility = View.GONE
        this@MainActivity.showErrorDialogWithAction(
            getString(R.string.error_connection_fail)
        ) { _, _ -> getMovieListFromApi() }
    }

    private fun getMovieListFromApiOnResponse(response: Response<List<MovieResponse>>) {
        loadingDialog.dismiss()
        if (response.isSuccessful && response.body() != null) {
            binding.mainRecyclerView.visibility = View.VISIBLE
            val movieListResponse = response.body()
            val movieList = mapToMovieList(movieListResponse)
            treatMovieListEmpty(movieList)
            val movieListAdapter = createMovieListAdapter(movieList)
            setupAdapter(movieListAdapter)
        } else {
            binding.mainRecyclerView.visibility = View.GONE
            this@MainActivity.showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> getMovieListFromApi() }
        }
    }

    private fun createMovieListAdapter(movieList: List<Movie>) =
        MovieListAdapter(movieList, onMovieButtonClickListener())

    private fun onMovieButtonClickListener() = object : OnMovieButtonClickListener {
        override fun onClick(movieId: Int) {
            val intent =
                Intent(this@MainActivity, MovieDetailsActivity::class.java)
            intent.putExtra(Constants.ID_MOVIE, movieId)
            startActivity(intent)
        }
    }

    private fun setupAdapter(movieListAdapter: MovieListAdapter) {
        binding.mainRecyclerView.adapter = movieListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.mainRecyclerView.layoutManager = layoutManager

    }

    private fun treatMovieListEmpty(movieList: List<Movie>) {
        if (movieList.isEmpty()) {
            binding.mainRecyclerView.visibility = View.GONE
            this.showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> getMovieListFromApi() }
        }
    }

    private fun mapToMovieList(movieListResponse: List<MovieResponse>?) =
        movieListResponse?.map {
            Movie(
                it.id ?: Constants.NULL_INT_RESPONSE,
                it.voteAverage ?: Constants.NULL_DOUBLE_RESPONSE,
                it.title?:Constants.NULL_STRING_RESPONSE,
                it.imageUrl.orEmpty(),
                it.releaseDate.orEmpty(),
            )
        } ?: emptyList()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.mainToolBar)
        supportActionBar?.title = getString(R.string.main_tool_bar_title_text)
    }
}