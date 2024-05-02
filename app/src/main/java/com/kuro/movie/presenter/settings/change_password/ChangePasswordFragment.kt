package com.kuro.movie.presenter.settings.change_password

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(
    inflater = FragmentChangePasswordBinding::inflate
) {
    private val viewModel: ChangePasswordViewModel by viewModels()
    override fun onInitialize() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.changePassword(
                binding.etOldPassword.text.toString(),
                binding.etNewPassword.text.toString(),
                binding.etReEnterPassword.text.toString()
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
            if (state.isChangeSuccess) {
                findNavController().popBackStack()
            }
            binding.progressBar.isVisible = state.isLoading
            binding.btnConfirm.isEnabled = !state.isLoading
            binding.etOldPassword.isEnabled = !state.isLoading
            binding.etNewPassword.isEnabled = !state.isLoading
            binding.etReEnterPassword.isEnabled = !state.isLoading
            binding.btnNavigateUp.isEnabled = !state.isLoading
        }
    }
}