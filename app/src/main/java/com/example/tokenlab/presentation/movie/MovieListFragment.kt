package com.example.tokenlab.presentation.movie

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenlab.R
import com.example.tokenlab.constants.Constants
import com.example.tokenlab.databinding.FragmentMovieListBinding
import com.example.tokenlab.di.ApplicationComponent
import com.example.tokenlab.di.DaggerApplicationComponent
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.extensions.createLoadingDialog
import com.example.tokenlab.extensions.showErrorDialogWithAction
import com.example.tokenlab.presentation.movie_details.MovieDetailsActivity
import javax.inject.Inject

class MovieListFragment : Fragment() {
    private val component: ApplicationComponent? by lazy {
        DaggerApplicationComponent.builder().build()
    }

    @Inject
    lateinit var viewModel: MovieListViewModel
    private var binding: FragmentMovieListBinding? = null
    private val loadingDialog: Dialog by lazy { requireContext().createLoadingDialog() }

    companion object {
        fun newInstance(): MovieListFragment = MovieListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component?.injectInMovieListActivity(this)
        setupObservers()
        setupToolBar()
        getMovieList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupObservers() {
        setupMovieListObserver()
        setupLoadingObserver()
        setupNetworkErrorObserver()
        setupGenericErrorObserver()
    }

    private fun setupLoadingObserver() {
        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            if (loading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun setupNetworkErrorObserver() {
        viewModel.networkError.observe(viewLifecycleOwner, {
            binding?.movieListRecyclerView?.visibility = View.GONE
            requireContext().showErrorDialogWithAction(
                getString(R.string.network_error)
            ) { _, _ -> getMovieList() }
        })
    }

    private fun setupGenericErrorObserver() {
        viewModel.genericError.observe(viewLifecycleOwner, {
            binding?.movieListRecyclerView?.visibility = View.GONE
            requireContext().showErrorDialogWithAction(
                getString(R.string.occurred_error)
            ) { _, _ -> getMovieList() }
        })
    }

    private fun setupMovieListObserver() {
        viewModel.movieList.observe(viewLifecycleOwner, { movieList ->
            if (movieList.isEmpty()) {
                treatMovieListEmpty()
            } else {
                binding?.movieListRecyclerView?.visibility = View.VISIBLE
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
                Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra(Constants.ID_MOVIE, movieId)
            startActivity(intent)
        }
        setupAdapter(movieListAdapter)
        movieListAdapter.setData(movieList)
    }

    private fun setupAdapter(movieListAdapter: MovieListAdapter) {
        binding?.movieListRecyclerView?.adapter = movieListAdapter
        val layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding?.movieListRecyclerView?.layoutManager = layoutManager
    }

    private fun treatMovieListEmpty() {
        binding?.movieListRecyclerView?.visibility = View.GONE
        requireContext().showErrorDialogWithAction(
            getString(R.string.occurred_error)
        ) { _, _ -> getMovieList() }
    }

    private fun setupToolBar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding?.movieListToolBar)
        }
    }
}