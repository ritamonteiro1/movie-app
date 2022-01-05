package com.example.tokenlab.presentation.movie

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemMovieBinding
import com.example.tokenlab.domain.model.movie.Movie
import com.example.tokenlab.extensions.convertToValidDateFormat
import com.example.tokenlab.extensions.downloadImage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class MovieListAdapter(private val onMovieButtonClickListener: (Int) -> Unit) :
    GroupAdapter<GroupieViewHolder>() {
    fun setData(movieList: List<Movie>) {
        movieList.forEach { movie ->
            add(MovieItem(movie))
        }
    }

    private inner class MovieItem(
        private val movie: Movie,
    ) : BindableItem<ItemMovieBinding>() {
        override fun bind(viewBinding: ItemMovieBinding, position: Int) {
            viewBinding.itemMovieImageView.downloadImage(movie.imageUrl)
            viewBinding.itemMovieTitleTextView.text = movie.title
            viewBinding.itemMovieDateTextView.text = movie.releaseDate.convertToValidDateFormat()
            viewBinding.itemMovieVoteAverageTextView.text = movie.voteAverage.toString()
            viewBinding.itemMovieButton.setOnClickListener { onMovieButtonClickListener.invoke(movie.id) }
        }

        override fun getLayout() = R.layout.item_movie

        override fun initializeViewBinding(view: View): ItemMovieBinding {
            return ItemMovieBinding.bind(view)
        }
    }
}