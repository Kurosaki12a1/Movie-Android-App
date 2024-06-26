package com.kuro.movie.presenter.person_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuro.movie.core.BaseViewModel
import com.kuro.movie.domain.usecase.person_detail.GetPersonDetailUseCase
import com.kuro.movie.util.Constants
import com.kuro.movie.util.Resource
import com.kuro.movie.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonDetailUseCase: GetPersonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableState = MutableLiveData(PersonDetailState())
    val state: LiveData<PersonDetailState>
        get() = mutableState

    init {
        PersonDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .personId.let {
                getPersonDetail(it)
            }
    }

    private fun getPersonDetail(personId: Int) {
        mutableState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val resource = async {
                getPersonDetailUseCase(
                    personId = personId,
                    language = Constants.DEFAULT_LANGUAGE
                )
            }
            withContext(Dispatchers.Main) {
                when (val result = resource.await()) {
                    is Resource.Success -> {
                        mutableState.update {
                            it.copy(
                                isLoading = false,
                                personDetail = result.value
                            )
                        }
                    }

                    is Resource.Failure -> {
                        mutableState.update { it.copy(isLoading = false) }
                        handleError(result.error)
                    }

                    is Resource.Loading -> {
                        mutableState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

}