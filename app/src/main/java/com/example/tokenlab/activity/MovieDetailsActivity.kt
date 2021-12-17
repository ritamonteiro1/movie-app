package com.example.tokenlab.activity

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenlab.R
import com.example.tokenlab.adapter.*
import com.example.tokenlab.api.Api
import com.example.tokenlab.api.DataService
import com.example.tokenlab.constants.Constants
import com.example.tokenlab.databinding.ActivityMovieDetailsBinding
import com.example.tokenlab.domains.movie.details.details.MovieDetails
import com.example.tokenlab.domains.movie.details.details.MovieDetailsResponse
import com.example.tokenlab.domains.movie.details.movie.details.list.MovieDetailsList
import com.example.tokenlab.domains.movie.details.production.company.ProductionCompany
import com.example.tokenlab.domains.movie.details.production.country.ProductionCountry
import com.example.tokenlab.domains.movie.details.spoken.language.SpokenLanguage
import com.example.tokenlab.extensions.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val loadingDialog: Dialog by lazy { createLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog.show()
        setupToolBar()
        val movieId = retrieverMovieId()
        getMovieDetailsFromApi(movieId)
    }

    private fun getMovieDetailsFromApi(clickedMovieId: Int) {
        val dataService: DataService = Api.setupRetrofit().create(DataService::class.java)
        val call: Call<MovieDetailsResponse> = dataService.recoverMovieDetails(clickedMovieId)
        call.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(
                call: Call<MovieDetailsResponse>,
                response: Response<MovieDetailsResponse>
            ) {
                getMovieDetailsFromApiOnResponse(response)
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                getMovieDetailsFromApiOnFailure()
            }
        })
    }

    private fun getMovieDetailsFromApiOnFailure() {
        loadingDialog.dismiss()
        setVisibilityGoneViews()
        this@MovieDetailsActivity.showErrorDialogWithAction(
            getString(R.string.error_connection_fail)
        ) { _, _ -> finish() }
    }

    private fun getMovieDetailsFromApiOnResponse(response: Response<MovieDetailsResponse>) {
        loadingDialog.dismiss()
        if (response.isSuccessful && response.body() != null) {
            val movieDetailsResponse = response.body()
            getMovieDetails(movieDetailsResponse)
        } else {
            setVisibilityGoneViews()
            this@MovieDetailsActivity.showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> finish() }
        }
    }

    private fun getMovieDetails(movieDetailsResponse: MovieDetailsResponse?) {
        val movieDetails = mapToMovieDetails(movieDetailsResponse)
        val movieDetailsList = mapToMovieDetailsList(movieDetails)
        val genres = mapToGenres(movieDetailsResponse)
        val productionCompanies = mapToProductionCompanies(movieDetailsResponse)
        val productionCountries = mapToProductionCountries(movieDetailsResponse)
        val spokenLanguages = mapToSpokenLanguages(movieDetailsResponse)
        showMovieDetails(
            movieDetails,
            movieDetailsList,
            genres,
            productionCompanies,
            productionCountries,
            spokenLanguages
        )
        setVisibilityVisibleViews()
    }

    private fun mapToMovieDetailsList(movieDetails: MovieDetails): List<MovieDetailsList> {
        return listOf(
            MovieDetailsList(
                getString(R.string.movie_details_release_date_movie_text),
                movieDetails.releaseDate.convertToValidDateFormat()
            ),
            MovieDetailsList(
                getString(R.string.movie_details_vote_count_text),
                movieDetails.voteCount.toString()
            ),
            MovieDetailsList(
                getString(R.string.movie_details_original_language_text),
                movieDetails.originalLanguage
            ),
            MovieDetailsList(
                getString(R.string.movie_details_original_title_text),
                movieDetails.originalTitle
            ),
            MovieDetailsList(
                getString(R.string.movie_details_belongs_to_collection_text),
                movieDetails.belongsToCollection
            ),
            MovieDetailsList(getString(R.string.movie_details_tagline_text), movieDetails.tagline)
        )
    }

    private fun mapToSpokenLanguages(movieDetailsResponse: MovieDetailsResponse?) =
        movieDetailsResponse?.spokenLanguages?.map {
            SpokenLanguage(it.name.convertIfIsNullOrBlank())
        } ?: emptyList()

    private fun mapToProductionCountries(movieDetailsResponse: MovieDetailsResponse?) =
        movieDetailsResponse?.productionCountries?.map {
            ProductionCountry(it.name.convertIfIsNullOrBlank())
        } ?: emptyList()

    private fun mapToProductionCompanies(movieDetailsResponse: MovieDetailsResponse?) =
        movieDetailsResponse?.productionCompanies?.map {
            ProductionCompany(
                it.name.convertIfIsNullOrBlank(),
                it.originCountry.convertIfIsNullOrBlank(),
                it.logoUrl.orEmpty()
            )
        } ?: emptyList()

    private fun mapToGenres(movieDetailsResponse: MovieDetailsResponse?) =
        movieDetailsResponse?.genres.orEmpty()

    private fun setVisibilityVisibleViews() {
        binding.movieDetailsImageView.visibility = View.VISIBLE
        binding.movieDetailsTitleTextView.visibility = View.VISIBLE
        binding.movieDetailsVoteAverageTextView.visibility = View.VISIBLE
        binding.movieDetailsGenresTextView.visibility = View.VISIBLE
        binding.movieDetailsGenderRecyclerView.visibility = View.VISIBLE
        binding.movieDetailsVoteTextView.visibility = View.VISIBLE
        binding.movieDetailsSpokenLanguagesTextView.visibility = View.VISIBLE
        binding.movieDetailsSpokenLanguagesRecyclerView.visibility = View.VISIBLE
        binding.movieDetailsProductionCountryTextView.visibility = View.VISIBLE
        binding.movieDetailsProductionCountryRecyclerView.visibility = View.VISIBLE
        binding.movieDetailsProductionCompanyTextView.visibility = View.VISIBLE
        binding.movieDetailsProductionCompanyRecyclerView.visibility = View.VISIBLE
        binding.movieDetailsRecyclerView.visibility = View.VISIBLE
    }

    private fun setVisibilityGoneViews() {
        binding.movieDetailsImageView.visibility = View.GONE
        binding.movieDetailsTitleTextView.visibility = View.GONE
        binding.movieDetailsVoteAverageTextView.visibility = View.GONE
        binding.movieDetailsGenresTextView.visibility = View.GONE
        binding.movieDetailsGenderRecyclerView.visibility = View.GONE
        binding.movieDetailsVoteTextView.visibility = View.GONE
        binding.movieDetailsSpokenLanguagesTextView.visibility = View.GONE
        binding.movieDetailsSpokenLanguagesRecyclerView.visibility = View.GONE
        binding.movieDetailsProductionCountryTextView.visibility = View.GONE
        binding.movieDetailsProductionCountryRecyclerView.visibility = View.GONE
        binding.movieDetailsProductionCompanyTextView.visibility = View.GONE
        binding.movieDetailsProductionCompanyRecyclerView.visibility = View.GONE
        binding.movieDetailsRecyclerView.visibility = View.GONE
    }

    private fun showMovieDetails(
        movieDetails: MovieDetails,
        movieDetailsList: List<MovieDetailsList>,
        movieDetailsGenres: List<String>,
        productionCompanies: List<ProductionCompany>,
        productionCountries: List<ProductionCountry>,
        spokenLanguage: List<SpokenLanguage>
    ) {
        binding.movieDetailsTitleTextView.text = movieDetails.title
        binding.movieDetailsVoteAverageTextView.text = movieDetails.voteAverage.toString()
        binding.movieDetailsImageView.downloadImage(movieDetails.imageUrl)
        val movieDetailsListAdapter = MovieDetailsListAdapter(movieDetailsList)
        setupMovieDetailsListAdapter(movieDetailsListAdapter)
        val genderListAdapter = GenderListAdapter(movieDetailsGenres)
        setupGenderListAdapter(genderListAdapter)
        val spokenLanguageListAdapter = SpokenLanguageListAdapter(spokenLanguage)
        setupSpokenLanguageListAdapter(spokenLanguageListAdapter)
        val productionCompanyListAdapter = ProductionCompanyListAdapter(productionCompanies)
        setupProductionCompanyListAdapter(productionCompanyListAdapter)
        val productionCountryListAdapter = ProductionCountryListAdapter(productionCountries)
        setupProductionCountryListAdapter(productionCountryListAdapter)
    }

    private fun setupMovieDetailsListAdapter(movieDetailsListAdapter: MovieDetailsListAdapter) {
        binding.movieDetailsRecyclerView.adapter = movieDetailsListAdapter
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

    private fun mapToMovieDetails(movieDetailsResponse: MovieDetailsResponse?): MovieDetails {
        return MovieDetails(
            movieDetailsResponse?.title.convertIfIsNullOrBlank(),
            movieDetailsResponse?.voteAverage ?: Constants.NULL_DOUBLE_RESPONSE,
            movieDetailsResponse?.voteCount ?: Constants.NULL_INT_RESPONSE,
            movieDetailsResponse?.releaseDate.orEmpty(),
            movieDetailsResponse?.imageUrl.orEmpty(),
            movieDetailsResponse?.originalLanguage.convertIfIsNullOrBlank(),
            movieDetailsResponse?.originalTitle.convertIfIsNullOrBlank(),
            movieDetailsResponse?.tagline.convertIfIsNullOrBlank(),
            movieDetailsResponse?.belongsToCollection?.name.convertIfIsNullOrBlank()
        )
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