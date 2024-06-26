package com.kuro.movie.presenter.filter_bottom_sheet

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kuro.movie.R
import com.kuro.movie.data.model.Genre

class GenreChipInflater(
    private val context: Context,
    private val genreListChipGroup: ChipGroup
) {

    fun createGenreChips(
        genreList: List<Genre>
    ) {
        clearChips()
        genreList.forEach { genre ->
            val genreChip = createGenreChip(
                genre = genre
            )
            genreListChipGroup.addView(genreChip)
        }
    }

    private fun createGenreChip(
        genre: Genre
    ): Chip {
        val chip = LayoutInflater.from(context).inflate(
            R.layout.chip, genreListChipGroup, false
        ) as Chip

        chip.text = genre.name
        chip.id = genre.id
        return chip
    }

    fun updateCheckedGenreFilters(checkedGenreIds: List<Int>) {

        if (checkedGenreIds.isEmpty()) {
            genreListChipGroup.clearCheck()
            return
        }
        checkedGenreIds.forEach {
            genreListChipGroup.check(it)
        }
    }

    private fun clearChips() {
        genreListChipGroup.removeAllViews()
    }
}