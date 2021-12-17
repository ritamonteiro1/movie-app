package com.example.tokenlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.example.tokenlab.click.listener.OnMovieButtonClickListener
import com.example.tokenlab.domains.movie.Movie
import com.example.tokenlab.extensions.convertToValidDateFormat
import com.example.tokenlab.extensions.downloadImage

class MovieListAdapter(
    private val movieList: List<Movie>,
    private val onMovieButtonClickListener: OnMovieButtonClickListener
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        return MovieListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(movieList[position], onMovieButtonClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemMovieImageView: ImageView = itemView.findViewById(R.id.itemMovieImageView)
        private val itemMovieTitleTextView: TextView =
            itemView.findViewById(R.id.itemMovieTitleTextView)
        private val itemMovieDateTextView: TextView =
            itemView.findViewById(R.id.itemMovieDateTextView)
        private val itemMovieVoteAverageTextView: TextView =
            itemView.findViewById(R.id.itemMovieVoteAverageTextView)
        private val itemMovieButton: Button = itemView.findViewById(R.id.itemMovieButton)

        fun bind(movieList: Movie, onMovieButtonClickListener: OnMovieButtonClickListener) {
            itemMovieImageView.downloadImage(movieList.imageUrl)
            itemMovieTitleTextView.text = movieList.title
            itemMovieDateTextView.text = movieList.releaseDate.convertToValidDateFormat()
            itemMovieVoteAverageTextView.text = movieList.voteAverage.toString()
            itemMovieButton.setOnClickListener { onMovieButtonClickListener.onClick(movieList.id) }
        }
    }
}