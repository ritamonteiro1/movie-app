package com.example.tokenlab.presentation.movie_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage

class SpokenLanguageListAdapter(private val spokenLanguageList: List<SpokenLanguage>) :
    RecyclerView.Adapter<SpokenLanguageListAdapter.SpokenListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpokenListViewHolder {
        return SpokenListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_spoken_language,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SpokenListViewHolder,
        position: Int
    ) {
        holder.bind(spokenLanguageList[position])
    }

    override fun getItemCount(): Int {
        return spokenLanguageList.size
    }

    inner class SpokenListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemSpokenLanguageTextView: TextView =
            itemView.findViewById(R.id.itemSpokenLanguageTextView)

        fun bind(spokenLanguage: SpokenLanguage) {
            itemSpokenLanguageTextView.text = spokenLanguage.name
        }
    }
}