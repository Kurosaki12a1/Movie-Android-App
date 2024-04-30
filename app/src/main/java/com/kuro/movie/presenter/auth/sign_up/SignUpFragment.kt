package com.kuro.movie.presenter.auth.sign_up

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSignUpBinding
import com.kuro.movie.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    inflater = FragmentSignUpBinding::inflate
) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onInitialize() {
        addOnBackPressedCallBack()
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.btnSignUp.setOnClickListener {
            viewModel.createUserWithEmailAndPassword(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        binding.txtSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.edtEmail.isEnabled = !isLoading
        binding.edtPassword.isEnabled = !isLoading
        binding.btnSignUp.isEnabled = !isLoading
    }

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    onLoading(true)
                }

                is Resource.Success -> {
                    onLoading(false)
                    findNavController().popBackStack()
                }

                is Resource.Failure -> {
                    onLoading(false)
                }
            }
        }
    }
}