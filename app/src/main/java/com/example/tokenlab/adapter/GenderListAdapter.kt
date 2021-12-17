package com.example.tokenlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.google.android.material.chip.Chip

class GenderListAdapter(private val genderList: List<String>) :
    RecyclerView.Adapter<GenderListAdapter.GenderListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenderListViewHolder {
        return GenderListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_gender,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GenderListViewHolder, position: Int) {
        holder.bind(genderList[position])
    }

    override fun getItemCount(): Int {
        return genderList.size
    }

    inner class GenderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemGenderChip: Chip = itemView.findViewById(R.id.itemGenderChip)

        fun bind(genderList: String) {
            itemGenderChip.text = genderList
        }
    }
}