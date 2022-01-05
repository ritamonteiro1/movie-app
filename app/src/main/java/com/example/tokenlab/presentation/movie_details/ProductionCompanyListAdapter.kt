package com.example.tokenlab.presentation.movie_details

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemProductionCompanyBinding
import com.example.tokenlab.domain.model.movie_details.production_company.ProductionCompany
import com.example.tokenlab.extensions.downloadImage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class ProductionCompanyListAdapter :
    GroupAdapter<GroupieViewHolder>() {
    fun setData(productionCompanyList: List<ProductionCompany>) {
        productionCompanyList.forEach { productionCompany ->
            add(ProductionCompanyItem(productionCompany))
        }
    }

    private inner class ProductionCompanyItem(
        private val productionCompany: ProductionCompany,
    ) : BindableItem<ItemProductionCompanyBinding>() {
        override fun bind(viewBinding: ItemProductionCompanyBinding, position: Int) {
            viewBinding.itemProductionCompanyNameTextView.text = productionCompany.name
            viewBinding.itemProductionCompanyCountryTextView.text = productionCompany.originCountry
            viewBinding.itemProductionCompanyImageView.downloadImage(productionCompany.logoUrl)
        }

        override fun getLayout() = R.layout.item_production_company

        override fun initializeViewBinding(view: View): ItemProductionCompanyBinding {
            return ItemProductionCompanyBinding.bind(view)
        }
    }
}