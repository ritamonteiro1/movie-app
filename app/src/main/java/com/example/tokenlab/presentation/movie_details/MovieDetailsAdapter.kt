package com.example.tokenlab.presentation.movie_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.example.tokenlab.domain.model.movie_details.movie_details_list.MovieDetailsItem

class MovieDetailsAdapter(private val movieDetailsItem: List<MovieDetailsItem>) :
    RecyclerView.Adapter<MovieDetailsAdapter.MovieDetailsListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieDetailsListViewHolder {
        return MovieDetailsListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_details,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MovieDetailsListViewHolder,
        position: Int
    ) {
        holder.bind(movieDetailsItem[position])
    }

    override fun getItemCount(): Int {
        return movieDetailsItem.size
    }

    inner class MovieDetailsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemMovieDetailsTypeTextView: TextView =
            itemView.findViewById(R.id.itemMovieDetailsTypeTextView)
        private val itemMovieDetailsValueTextView: TextView =
            itemView.findViewById(R.id.itemMovieDetailsValueTextView)

        fun bind(movieDetailsItem: MovieDetailsItem) {
            itemMovieDetailsTypeTextView.text = movieDetailsItem.type
            itemMovieDetailsValueTextView.text = movieDetailsItem.value
        }
    }
}