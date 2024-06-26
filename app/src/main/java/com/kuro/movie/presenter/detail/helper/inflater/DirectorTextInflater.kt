package com.kuro.movie.presenter.detail.helper.inflater

import android.content.Context
import android.view.ViewGroup
import com.kuro.movie.domain.model.Crew

class DirectorTextInflater(
    private val context: Context,
    private val viewGroup: ViewGroup
) : BaseTextInflater(context, viewGroup) {

    private val DIRECTION_DEPARTMENT_NAME = "Directing"
    fun createDirectorText(
        crews: List<Crew>?
    ) {
        if (crews.isNullOrEmpty()) {
            viewGroup.removeAllViews()
            return
        }
        val director = crews.find {
            it.department == DIRECTION_DEPARTMENT_NAME
        }

        director?.let {
            val directorText = createText(
                name = it.name,
                id = it.id
            )
            viewGroup.addView(directorText)
        }
    }
}