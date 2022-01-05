package com.example.tokenlab.presentation.movie_details

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemGenderBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class GenderListAdapter : GroupAdapter<GroupieViewHolder>() {
    fun setData(genderList: List<String>) {
        genderList.forEach { gender ->
            add(GenderItem(gender))
        }
    }

    private inner class GenderItem(private val gender: String) :
        BindableItem<ItemGenderBinding>() {
        override fun bind(viewBinding: ItemGenderBinding, position: Int) {
            viewBinding.itemGenderChip.text = gender
        }

        override fun getLayout(): Int = R.layout.item_gender

        override fun initializeViewBinding(view: View): ItemGenderBinding {
            return ItemGenderBinding.bind(view)
        }

    }
}
