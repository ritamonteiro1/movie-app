package com.example.tokenlab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlab.R
import com.example.tokenlab.domains.movie.details.production.country.ProductionCountry

class ProductionCountryListAdapter(private val productionCountryList: List<ProductionCountry>) :
    RecyclerView.Adapter<ProductionCountryListAdapter.ProductionCountryViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductionCountryViewHolder {
        return ProductionCountryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_production_country,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ProductionCountryViewHolder,
        position: Int
    ) {
        holder.bind(productionCountryList[position])
    }

    override fun getItemCount(): Int {
        return productionCountryList.size
    }

    inner class ProductionCountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemProductionCountryTextView: TextView =
            itemView.findViewById(R.id.itemProductionCountryTextView)

        fun bind(productionCountry: ProductionCountry) {
            itemProductionCountryTextView.text = productionCountry.name
        }
    }
}