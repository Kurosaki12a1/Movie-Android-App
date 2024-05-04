package com.kuro.movie.presenter.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuro.movie.databinding.FragmentBottomSheetBinding
import com.kuro.movie.domain.model.Category
import com.kuro.movie.domain.model.Sort
import com.kuro.movie.domain.model.isMovie
import com.kuro.movie.domain.model.isPopularity
import com.kuro.movie.presenter.explore.ExploreBottomSheetEvent
import com.kuro.movie.presenter.explore.ExploreViewModel

class FilterBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExploreViewModel by activityViewModels()

    private var genreChipInflater: GenreChipInflater? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreChipInflater = GenreChipInflater(
            context = requireContext(),
            genreListChipGroup = binding.genreListGroup
        )
        onInitialize()
    }

    override fun onResume() {
        super.onResume()
        genreChipInflater?.updateCheckedGenreFilters(
            viewModel.filterBottomSheetState.value?.checkedGenreIdsState ?: emptyList()
        )
    }

    private fun onInitialize() {
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val category =
                if (group.checkedChipId == binding.movieChip.id) Category.MOVIE else Category.TV

            binding.genreListGroup.clearCheck()

            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateCategory(category))
        }

        binding.genreListGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateGenreList(checkedIds))
        }

        binding.sortChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val checkedSort =
                if (group.checkedChipId == binding.popularity.id) Sort.Popularity else Sort.LatestRelease
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateSort(checkedSort))
        }

        binding.btnReset.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.ResetFilterBottomState)
        }

        binding.btnApply.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.Apply)
        }
    }

    private fun setUpObservers() {
        viewModel.filterBottomSheetState.observe(viewLifecycleOwner) { filterBottomSheet ->
            if (!filterBottomSheet.isExpanded) {
                findNavController().popBackStack()
            } else {
                // Expand then start do these things.
                updateCheckCategoryFilter(filterBottomSheet.categoryState)
                genreChipInflater?.updateCheckedGenreFilters(filterBottomSheet.checkedGenreIdsState)
                updateCheckedSortFilter(filterBottomSheet.checkedSortState)
            }
        }

        viewModel.genreList.observe(viewLifecycleOwner) { genre ->
            genreChipInflater?.createGenreChips(genreList = genre)
            if (viewModel.filterBottomSheetState.value?.checkedGenreIdsState != null) {
                genreChipInflater?.updateCheckedGenreFilters(viewModel.filterBottomSheetState.value!!.checkedGenreIdsState)
            }
        }
    }

    private fun updateCheckedSortFilter(checkedSortState: Sort) {
        val checkedSortId =
            if (checkedSortState.isPopularity()) binding.popularity.id else binding.latestRelease.id

        binding.sortChipGroup.check(checkedSortId)
    }

    private fun updateCheckCategoryFilter(categoryState: Category) {
        val chipId = if (categoryState.isMovie()) {
            binding.movieChip.id
        } else binding.tvSeriesChip.id

        binding.categoriesChipGroup.check(chipId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        genreChipInflater = null
    }
}