package com.example.tokenlab.presentation.movie_details

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemProductionCountryBinding
import com.example.tokenlab.domain.model.movie_details.production_country.ProductionCountry
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class ProductionCountryListAdapter :
    GroupAdapter<GroupieViewHolder>() {
    fun setData(productionCountryList: List<ProductionCountry>) {
        productionCountryList.forEach { productionCountry ->
            add(ProductionCountryItem(productionCountry))
        }
    }

    private inner class ProductionCountryItem(
        private val productionCountry: ProductionCountry,
    ) : BindableItem<ItemProductionCountryBinding>() {
        override fun bind(viewBinding: ItemProductionCountryBinding, position: Int) {
            viewBinding.itemProductionCountryTextView.text = productionCountry.name
        }

        override fun getLayout() = R.layout.item_production_country

        override fun initializeViewBinding(view: View): ItemProductionCountryBinding {
            return ItemProductionCountryBinding.bind(view)
        }
    }
}
