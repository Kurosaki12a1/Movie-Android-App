package com.kuro.movie.presenter.explore

import com.kuro.movie.domain.model.Category
import com.kuro.movie.domain.model.Sort

sealed class ExploreBottomSheetEvent {
    data class UpdateCategory(val checkedCategory: Category) : ExploreBottomSheetEvent()
    data class UpdateGenreList(val checkedList: List<Int>) : ExploreBottomSheetEvent()
    data class UpdateSort(val checkedSort: Sort) : ExploreBottomSheetEvent()
    data object OpenFilter : ExploreBottomSheetEvent()
    data object ResetFilterBottomState : ExploreBottomSheetEvent()
    data object Apply : ExploreBottomSheetEvent()
}