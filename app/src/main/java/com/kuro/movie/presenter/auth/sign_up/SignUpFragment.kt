package com.kuro.movie.presenter.auth.sign_up

import android.view.View
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

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().popBackStack()
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }

        }
    }
}