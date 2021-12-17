package com.example.tokenlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.example.tokenlab.domains.movie.details.movie.details.list.MovieDetailsList

class MovieDetailsListAdapter(private val movieDetailsList: List<MovieDetailsList>) :
    RecyclerView.Adapter<MovieDetailsListAdapter.MovieDetailsListViewHolder>() {


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
        holder.bind(movieDetailsList[position])
    }

    override fun getItemCount(): Int {
        return movieDetailsList.size
    }

    inner class MovieDetailsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemMovieDetailsTypeTextView: TextView =
            itemView.findViewById(R.id.itemMovieDetailsTypeTextView)
        private val itemMovieDetailsValueTextView: TextView =
            itemView.findViewById(R.id.itemMovieDetailsValueTextView)

        fun bind(movieDetailsList: MovieDetailsList) {
            itemMovieDetailsTypeTextView.text = movieDetailsList.movieDetailsType
            itemMovieDetailsValueTextView.text = movieDetailsList.movieDetailsValue
        }
    }
}