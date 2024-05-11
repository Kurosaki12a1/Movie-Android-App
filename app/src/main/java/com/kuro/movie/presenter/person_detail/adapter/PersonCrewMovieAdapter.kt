package com.kuro.movie.presenter.person_detail.adapter

import com.kuro.movie.domain.model.CrewForPerson


class PersonCrewMovieAdapter : PersonMovieBaseAdapter<CrewForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val crew = getItem(position)
        holder.bindCrew(crew = crew)
        holder.itemView.setOnClickListener {
            super.clickListener(crew)
        }
    }
}