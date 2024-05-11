package com.kuro.movie.presenter.person_detail

import android.text.method.ScrollingMovementMethod
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentPersonDetailBinding
import com.kuro.movie.domain.model.CastForPerson
import com.kuro.movie.domain.model.CrewForPerson
import com.kuro.movie.domain.model.MediaType
import com.kuro.movie.domain.model.toMovie
import com.kuro.movie.domain.model.toTvSeries
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.presenter.person_detail.adapter.PersonCastMovieAdapter
import com.kuro.movie.presenter.person_detail.adapter.PersonCrewMovieAdapter
import com.kuro.movie.presenter.person_detail.helper.BindPersonDetailHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(
    inflater = FragmentPersonDetailBinding::inflate
) {
    private val viewModel: PersonDetailViewModel by viewModels()

    private var personCrewAdapter: PersonCrewMovieAdapter? = null
    private var personCastAdapter: PersonCastMovieAdapter? = null
    private var bindingPersonDetailHelper: BindPersonDetailHelper? = null

    override fun onInitialize() {
        setUpAdapters()
        setUpViews()
        setUpObservers()
        addOnBackPressedCallBack()
    }

    private fun setUpAdapters() {
        personCrewAdapter = PersonCrewMovieAdapter()
        personCastAdapter = PersonCastMovieAdapter()

        bindingPersonDetailHelper = BindPersonDetailHelper(
            binding = binding,
            personCrewAdapter = personCrewAdapter!!,
            personCastAdapter = personCastAdapter!!,
            context = requireContext()
        )
    }

    private fun setUpViews() {
        binding.txtBio.movementMethod = ScrollingMovementMethod()

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        personCastAdapter?.setOnClickListener { castForPerson ->
            val action = setupAction(castForPerson = castForPerson)
            navigateToFlow(action)
        }

        personCrewAdapter?.setOnClickListener { crewForPerson ->
            val action = setupAction(crewForPerson = crewForPerson)
            navigateToFlow(action)
        }
    }

    private fun setupAction(crewForPerson: CrewForPerson): NavigateFlow.BottomSheetDetailFlow {
        return when (crewForPerson.mediaType) {
            MediaType.MOVIE.value -> {
                NavigateFlow.BottomSheetDetailFlow(
                    movie = crewForPerson.toMovie(),
                    tvSeries = null
                )
            }

            MediaType.TV_SERIES.value -> {
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = crewForPerson.toTvSeries()
                )
            }

            else -> {
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = null
                )
            }
        }
    }

    private fun setupAction(castForPerson: CastForPerson): NavigateFlow.BottomSheetDetailFlow {
        return when (castForPerson.mediaType) {
            MediaType.MOVIE.value -> {
                NavigateFlow.BottomSheetDetailFlow(
                    movie = castForPerson.toMovie(),
                    tvSeries = null
                )
            }

            MediaType.TV_SERIES.value -> {
                NavigateFlow.BottomSheetDetailFlow(
                    movie = null,
                    tvSeries = castForPerson.toTvSeries()
                )
            }

            else -> NavigateFlow.BottomSheetDetailFlow(
                movie = null,
                tvSeries = null
            )
        }
    }

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            state.personDetail?.let { personDetail ->
                bindingPersonDetailHelper?.bindPersonDetail(personDetail = personDetail)
            }
        }
    }

    override fun onDestroyView() {
        personCastAdapter = null
        personCrewAdapter = null
        bindingPersonDetailHelper = null
        super.onDestroyView()
    }
}