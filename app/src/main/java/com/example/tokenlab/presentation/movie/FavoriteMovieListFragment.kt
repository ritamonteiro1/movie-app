package com.example.tokenlab.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tokenlab.databinding.FragmentFavoriteMovieListBinding

class FavoriteMovieListFragment : Fragment() {
    private var binding: FragmentFavoriteMovieListBinding? = null

    companion object {
        fun newInstance(): FavoriteMovieListFragment = FavoriteMovieListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMovieListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupToolBar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding?.favoriteMovieListToolBar)
        }
    }

}