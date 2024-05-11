package com.kuro.movie.presenter.person_detail

import com.kuro.movie.domain.model.PersonDetail

data class PersonDetailState(
    val isLoading: Boolean = false,
    val personDetail: PersonDetail? = null
)
