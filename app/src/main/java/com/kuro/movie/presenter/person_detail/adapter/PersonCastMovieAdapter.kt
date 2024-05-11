package com.kuro.movie.presenter.person_detail.adapter

import com.kuro.movie.domain.model.CastForPerson

class PersonCastMovieAdapter : PersonMovieBaseAdapter<CastForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val castForPerson = getItem(position)
        holder.bindCast(cast = castForPerson)
        holder.itemView.setOnClickListener {
            super.clickListener(castForPerson)
        }
    }
}
