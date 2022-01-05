package com.example.tokenlab.presentation.movie_details

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemMovieDetailsBinding
import com.example.tokenlab.domain.model.movie_details.movie_details_list.MovieDetailsElement
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class MovieDetailsElementListAdapter : GroupAdapter<GroupieViewHolder>() {
    fun setData(movieDetailsElementList: List<MovieDetailsElement>) {
        movieDetailsElementList.forEach { movieDetailsElement ->
            add(MovieDetailsItem(movieDetailsElement))
        }
    }

    private inner class MovieDetailsItem(private val movieDetailsElement: MovieDetailsElement) :
        BindableItem<ItemMovieDetailsBinding>() {
        override fun bind(viewBinding: ItemMovieDetailsBinding, position: Int) {
            viewBinding.itemMovieDetailsTypeTextView.text = movieDetailsElement.type
            viewBinding.itemMovieDetailsValueTextView.text = movieDetailsElement.value
        }

        override fun getLayout(): Int = R.layout.item_movie_details

        override fun initializeViewBinding(view: View): ItemMovieDetailsBinding {
            return ItemMovieDetailsBinding.bind(view)
        }

    }
}