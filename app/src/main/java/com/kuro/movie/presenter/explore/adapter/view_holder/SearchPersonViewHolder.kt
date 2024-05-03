package com.kuro.movie.presenter.explore.adapter.view_holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.kuro.movie.R
import com.kuro.movie.databinding.SearchPersonRowBinding
import com.kuro.movie.domain.model.PersonSearch
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil


class SearchPersonViewHolder(
    val binding: SearchPersonRowBinding,
    val context: Context
) : ViewHolder(binding.root) {


    fun bindPerson(
        personSearch: PersonSearch,
        onClickPersonItem: (PersonSearch) -> Unit = {}
    ) {
        binding.ivProfile.load(
            ImageUtil.getImage(
                imageUrl = personSearch.profilePath,
                imageSize = ImageSize.W500.path
            )
        ) {
            error(R.drawable.ic_person_white)
        }

        binding.txtCategory.visibility = View.VISIBLE

        binding.txPersonName.text = personSearch.name
        personSearch.knownForDepartment?.let {
            binding.tvKnownForDepartment.text = personSearch.knownForDepartment
        }

        binding.root.setOnClickListener {
            onClickPersonItem(personSearch)
        }
    }


    companion object {
        fun from(
            parent: ViewGroup
        ): SearchPersonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchPersonRowBinding.inflate(layoutInflater, parent, false)
            return SearchPersonViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}