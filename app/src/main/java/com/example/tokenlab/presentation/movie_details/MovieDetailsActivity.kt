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
import com.example.tokenlab.domain.model.movie_details.movie_details_list.MovieDetailsElement
import com.example.tokenlab.domain.model.movie_details.production_company.ProductionCompany
import com.example.tokenlab.domain.model.movie_details.production_country.ProductionCountry
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage
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
            val movieDetailsElementList = mapToMovieDetailsElementList(movieDetails)
            showMovieDetails(movieDetails, movieDetailsElementList)
            setScrollViewVisibility(View.VISIBLE)
        })
    }

    private fun setScrollViewVisibility(visibility: Int) {
        binding.movieDetailsScrollView.visibility = visibility
    }

    private fun mapToMovieDetailsElementList(movieDetails: MovieDetails): List<MovieDetailsElement> {
        return listOf(
            MovieDetailsElement(
                getString(R.string.movie_details_release_date_movie_text),
                movieDetails.releaseDate.convertToValidDateFormat()
            ),
            MovieDetailsElement(
                getString(R.string.movie_details_vote_count_text),
                movieDetails.voteCount.toString()
            ),
            MovieDetailsElement(
                getString(R.string.movie_details_original_language_text),
                movieDetails.originalLanguage
            ),
            MovieDetailsElement(
                getString(R.string.movie_details_original_title_text),
                movieDetails.originalTitle
            ),
            MovieDetailsElement(
                getString(R.string.movie_details_tagline_text),
                movieDetails.tagline
            )
        )
    }

    private fun showMovieDetails(
        movieDetails: MovieDetails,
        movieDetailElementList: List<MovieDetailsElement>
    ) {
        binding.movieDetailsTitleTextView.text = movieDetails.title
        binding.movieDetailsVoteAverageTextView.text = movieDetails.voteAverage.toString()
        binding.movieDetailsImageView.downloadImage(movieDetails.imageUrl)
        createMovieDetailsElementListAdapter(movieDetailElementList)
        createGenderListAdapter(movieDetails.genres)
        createSpokenLanguageListAdapter(movieDetails.spokenLanguages)
        createProductionCompanyListAdapter(movieDetails.productionCompanies)
        createProductionCountryListAdapter(movieDetails.productionCountries)
    }

    private fun createProductionCountryListAdapter(productionCountryList: List<ProductionCountry>){
        val productionCountryListAdapter = ProductionCountryListAdapter()
        setupProductionCountryListAdapter(productionCountryListAdapter)
        productionCountryListAdapter.setData(productionCountryList)
    }

    private fun createProductionCompanyListAdapter(productionCompanyList: List<ProductionCompany>) {
        val productionCompanyListAdapter = ProductionCompanyListAdapter()
        setupProductionCompanyListAdapter(productionCompanyListAdapter)
        productionCompanyListAdapter.setData(productionCompanyList)
    }

    private fun createSpokenLanguageListAdapter(spokenLanguageList: List<SpokenLanguage>) {
        val spokenLanguageListAdapter = SpokenLanguageListAdapter()
        setupSpokenLanguageListAdapter(spokenLanguageListAdapter)
        spokenLanguageListAdapter.setData(spokenLanguageList)
    }

    private fun createGenderListAdapter(genderList: List<String>) {
        val genderListAdapter = GenderListAdapter()
        setupGenderListAdapter(genderListAdapter)
        genderListAdapter.setData(genderList)
    }

    private fun createMovieDetailsElementListAdapter(movieDetailElementList: List<MovieDetailsElement>) {
        val movieDetailsElementListAdapter = MovieDetailsElementListAdapter()
        setupMovieDetailsAdapter(movieDetailsElementListAdapter)
        movieDetailsElementListAdapter.setData(movieDetailElementList)
    }

    private fun setupMovieDetailsAdapter(movieDetailsElementListAdapter: MovieDetailsElementListAdapter) {
        binding.movieDetailsRecyclerView.adapter = movieDetailsElementListAdapter
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