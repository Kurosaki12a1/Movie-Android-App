package com.kuro.movie.presenter.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kuro.movie.R
import com.kuro.movie.databinding.ActorRowBinding
import com.kuro.movie.domain.model.Cast
import com.kuro.movie.util.ImageSize
import com.kuro.movie.util.ImageUtil

class DetailActorAdapter :
    ListAdapter<Cast, DetailActorAdapter.DetailActorAdapterViewHolder>(castItemCallback) {

    private var itemClickListener: (actorId: Int) -> Unit = {}

    class DetailActorAdapterViewHolder(
        private val binding: ActorRowBinding,
        private val onItemClickListener: (actorId: Int) -> Unit
    ) : ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_person_white)

            Glide.with(binding.imvProfilePhoto.context)
                .load(
                    ImageUtil.getImage(
                        imageUrl = cast.profilePath,
                    )
                )
                .apply(requestOptions)
                .into(binding.imvProfilePhoto)

            binding.txtActorName.text = cast.name
            binding.txtCharacterName.text = cast.character

            binding.root.setOnClickListener {
                onItemClickListener(cast.id)
            }

        }

        companion object {
            fun from(
                parent: ViewGroup, onItemClickListener: (actorId: Int) -> Unit
            ): DetailActorAdapterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ActorRowBinding.inflate(inflater, parent, false)
                return DetailActorAdapterViewHolder(
                    binding = binding, onItemClickListener = onItemClickListener
                )
            }
        }

    }

    fun setActorTextListener(listener: (actorId: Int) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DetailActorAdapterViewHolder {
        return DetailActorAdapterViewHolder.from(
            parent = parent, onItemClickListener = itemClickListener
        )
    }

    override fun onBindViewHolder(holder: DetailActorAdapterViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }
}


val castItemCallback = object : ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }
}