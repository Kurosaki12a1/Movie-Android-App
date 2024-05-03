package com.kuro.movie.presenter.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentSettingsBinding
import com.kuro.movie.extension.toPx
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.util.AlertDialogUtil
import com.kuro.movie.util.Constants.AVATAR_SIZE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    inflater = FragmentSettingsBinding::inflate
) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onInitialize() {
        setUpView()
        setUpObservers()
        addOnBackPressedCallBack {
            findNavController().popBackStack()
            Unit
        }
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
            navigateToFlow(NavigateFlow.ProfileFlow)
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

    private fun bindUser(user: FirebaseUser?) {
        if (user == null) return
        binding.tvEmail.text = user.email
        binding.tvUserName.text = user.displayName

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.avatar_place_holder)
            .transform(CenterCrop(), CircleCrop())
            .override(AVATAR_SIZE.toPx(requireContext()))

        Glide.with(this)
            .load(user.photoUrl)
            .apply(requestOptions)
            .into(binding.avatarUser)
    }

    private fun updateUIMode(uiMode: Int) {
        viewModel.updateUIMode(uiMode)
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.txtLogOut.isVisible = state.isSignedIn

            bindUser(state.userProfile)
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