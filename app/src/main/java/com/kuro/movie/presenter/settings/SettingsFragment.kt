package com.kuro.movie.presenter.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSettingsBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.util.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    inflater = FragmentSettingsBinding::inflate
) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onInitialize() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.txtLogOut.setOnClickListener {
            AlertDialogUtil.showAlertDialog(
                context = requireContext(),
                title = R.string.are_you_sure_log_out,
                message = R.string.log_out_message,
                positiveBtnMessage = R.string.log_out,
                negativeBtnMessage = R.string.cancel,
                onClickPositiveButton = {
                    viewModel.logOut()
                }
            )
        }

        binding.profileContainer.setOnClickListener {
            (requireActivity() as NavigationFlow).navigateToFlow(NavigateFlow.ProfileFlow)
        }

        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Update uiMode to DarkTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Update uiMode to LightTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateUIMode(uiMode: Int) {
        viewModel.updateUIMode(uiMode)
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.txtLogOut.isVisible = state.isSignedIn

            val isDarkTheme = state.uiMode == AppCompatDelegate.MODE_NIGHT_YES
            binding.switchDarkTheme.isChecked = isDarkTheme
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}