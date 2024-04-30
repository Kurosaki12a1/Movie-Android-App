package com.kuro.movie.presenter.auth.forget_password

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentForgetPasswordBinding
import com.kuro.movie.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>(
    inflater = FragmentForgetPasswordBinding::inflate
) {

    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onInitialize() {
        addOnBackPressedCallBack()
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.txtBackToLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnForgetPassword.setOnClickListener {
            viewModel.sendPasswordResetToEmail(binding.edtEmail.text.toString())
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.edtEmail.isEnabled = !isLoading
        binding.btnForgetPassword.isEnabled = !isLoading
    }

    private fun setUpObservers() {
        viewModel.snackBarMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.emailState.observe(viewLifecycleOwner) {
            binding.edtEmail.setText(it)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Success -> {
                    onLoading(false)
                    findNavController().popBackStack()
                }

                is Resource.Failure -> {
                    onLoading(false)
                }

                is Resource.Loading -> {
                    onLoading(true)
                }
            }
        }
    }
}