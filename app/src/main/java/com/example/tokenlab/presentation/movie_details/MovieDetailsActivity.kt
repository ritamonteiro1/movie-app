package com.example.tokenlab.presentation.movie_details

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenlab.R
import com.example.tokenlab.constants.Constants
import com.example.tokenlab.data.api.Api
import com.example.tokenlab.data.api.MovieDataService
import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSource
import com.example.tokenlab.data.remote.remote_data_source.MovieRemoteDataSourceImpl
import com.example.tokenlab.data.repository.MovieRepository
import com.example.tokenlab.databinding.ActivityMovieDetailsBinding
import com.example.tokenlab.domain.data_repository.MovieDataRepository
import com.example.tokenlab.domain.model.movie_details.details.MovieDetails
import com.example.tokenlab.domain.model.movie_details.movie_details_list.MovieDetailsItem
import com.example.tokenlab.domain.use_case.GetMovieDetailsUseCase
import com.example.tokenlab.domain.use_case.GetMovieDetailsUseCaseImpl
import com.example.tokenlab.extensions.*
import com.example.tokenlab.presentation.movie_details.*
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {
    private val movieDataService: MovieDataService =
        Api.setupRetrofit().create(MovieDataService::class.java)
    private val movieRemoteDataSource: MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(movieDataService)
    private val movieRepository: MovieDataRepository = MovieRepository(movieRemoteDataSource)
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase =
        GetMovieDetailsUseCaseImpl(movieRepository)
    private val viewModel: MovieDetailsViewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
    private lateinit var binding: ActivityMovieDetailsBinding
    private val loadingDialog: Dialog by lazy { createLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupToolBar()
        val movieId = retrieverMovieId()
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModel.getMovieDetails(movieId)
    }

    private fun setupObservers() {
        setupMovieDetailsObserver()
        setupLoadingObserver()
        setupNetworkErrorObserver()
        setupGenericErrorObserver()
    }

    private fun setupNetworkErrorObserver() {
        setScrollViewVisibility(View.GONE)
        viewModel.networkError.observe(this, {
                this@MovieDetailsActivity.showErrorDialogWithAction(
                    getString(R.string.network_error)
                ) { _, _ -> finish() }
        })
    }

    private fun setupGenericErrorObserver() {
        setScrollViewVisibility(View.GONE)
        viewModel.genericError.observe(this, {
            this@MovieDetailsActivity.showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> finish() }
        })
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

    private fun setupMovieDetailsObserver() {
        viewModel.movieDetails.observe(this, { movieDetails ->
            val movieDetailsList = mapToMovieDetailsList(movieDetails)
            showMovieDetails(movieDetails, movieDetailsList)
            setScrollViewVisibility(View.VISIBLE)
        })
    }

    private fun setScrollViewVisibility(visibility: Int){
        binding.movieDetailsScrollView.visibility = visibility
    }

    private fun mapToMovieDetailsList(movieDetails: MovieDetails): List<MovieDetailsItem> {
        return listOf(
            MovieDetailsItem(
                getString(R.string.movie_details_release_date_movie_text),
                movieDetails.releaseDate.convertToValidDateFormat()
            ),
            MovieDetailsItem(
                getString(R.string.movie_details_vote_count_text),
                movieDetails.voteCount.toString()
            ),
            MovieDetailsItem(
                getString(R.string.movie_details_original_language_text),
                movieDetails.originalLanguage
            ),
            MovieDetailsItem(
                getString(R.string.movie_details_original_title_text),
                movieDetails.originalTitle
            ),
            MovieDetailsItem(getString(R.string.movie_details_tagline_text), movieDetails.tagline)
        )
    }

    private fun showMovieDetails(
        movieDetails: MovieDetails,
        movieDetailsItem: List<MovieDetailsItem>
    ) {
        binding.movieDetailsTitleTextView.text = movieDetails.title
        binding.movieDetailsVoteAverageTextView.text = movieDetails.voteAverage.toString()
        binding.movieDetailsImageView.downloadImage(movieDetails.imageUrl)
        val movieDetailsListAdapter = MovieDetailsAdapter(movieDetailsItem)
        setupMovieDetailsListAdapter(movieDetailsListAdapter)
        val genderListAdapter = GenderListAdapter(movieDetails.genres)
        setupGenderListAdapter(genderListAdapter)
        val spokenLanguageListAdapter = SpokenLanguageListAdapter(movieDetails.spokenLanguages)
        setupSpokenLanguageListAdapter(spokenLanguageListAdapter)
        val productionCompanyListAdapter =
            ProductionCompanyListAdapter(movieDetails.productionCompanies)
        setupProductionCompanyListAdapter(productionCompanyListAdapter)
        val productionCountryListAdapter =
            ProductionCountryListAdapter(movieDetails.productionCountries)
        setupProductionCountryListAdapter(productionCountryListAdapter)
    }

    private fun setupMovieDetailsListAdapter(movieDetailsAdapter: MovieDetailsAdapter) {
        binding.movieDetailsRecyclerView.adapter = movieDetailsAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.movieDetailsRecyclerView.layoutManager = layoutManager
    }

    private fun setupProductionCountryListAdapter(productionCountryListAdapter: ProductionCountryListAdapter) {
        binding.movieDetailsProductionCountryRecyclerView.adapter = productionCountryListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.movieDetailsProductionCountryRecyclerView.layoutManager = layoutManager
    }

    private fun setupProductionCompanyListAdapter(productionCompanyListAdapter: ProductionCompanyListAdapter) {
        binding.movieDetailsProductionCompanyRecyclerView.adapter = productionCompanyListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.movieDetailsProductionCompanyRecyclerView.layoutManager = layoutManager
    }

    private fun setupSpokenLanguageListAdapter(spokenLanguageListAdapter: SpokenLanguageListAdapter) {
        binding.movieDetailsSpokenLanguagesRecyclerView.adapter = spokenLanguageListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.movieDetailsSpokenLanguagesRecyclerView.layoutManager = layoutManager
    }

    private fun setupGenderListAdapter(genderListAdapter: GenderListAdapter) {
        binding.movieDetailsGenderRecyclerView.adapter = genderListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.movieDetailsGenderRecyclerView.layoutManager = layoutManager
    }

    private fun retrieverMovieId(): Int {
        return intent.getSerializableExtra(Constants.ID_MOVIE) as Int
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolBar() {
        setSupportActionBar(binding.movieDetailsToolBar)
        supportActionBar?.title = getString(R.string.movie_details_tool_bar_title_text)
    }
}