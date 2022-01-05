package com.example.tokenlab.presentation.movie_details

import android.view.View
import com.example.tokenlab.R
import com.example.tokenlab.databinding.ItemSpokenLanguageBinding
import com.example.tokenlab.domain.model.movie_details.spoken_language.SpokenLanguage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class SpokenLanguageListAdapter :
    GroupAdapter<GroupieViewHolder>() {
    fun setData(spokenLanguageList: List<SpokenLanguage>) {
        spokenLanguageList.forEach { spokenLanguage ->
            add(SpokenLanguageItem(spokenLanguage))
        }
    }

    private inner class SpokenLanguageItem(
        private val spokenLanguage: SpokenLanguage,
    ) : BindableItem<ItemSpokenLanguageBinding>() {
        override fun bind(viewBinding: ItemSpokenLanguageBinding, position: Int) {
            viewBinding.itemSpokenLanguageTextView.text = spokenLanguage.name
        }

        override fun getLayout() = R.layout.item_spoken_language

        override fun initializeViewBinding(view: View): ItemSpokenLanguageBinding {
            return ItemSpokenLanguageBinding.bind(view)
        }
    }
}