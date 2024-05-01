package com.kuro.movie.presenter.settings.profile

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentProfileBinding
import com.kuro.movie.extension.toPx
import com.kuro.movie.util.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    inflater = FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()
    override fun onInitialize() {
        addOnBackPressedCallBack()
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

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
    }

    private fun onLoading(isLoading: Boolean) {
        binding.txtLogOut.isEnabled = !isLoading
        binding.txtChangePassword.isEnabled = !isLoading
        binding.txtChangeName.isEnabled = !isLoading
        binding.progressBar.isVisible = isLoading
        binding.txtDeleteAccount.isEnabled = !isLoading
    }

    private fun bindProfile(user: FirebaseUser?) {
        if (user == null) return
        binding.tvUserName.text = user.displayName
        binding.tvEmail.text = user.email

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.avatar_place_holder)
            .transform(CenterCrop(), RoundedCorners(16))
            .override(60f.toPx(requireContext()))

        Glide.with(this)
            .load(user.photoUrl)
            .apply(requestOptions)
            .into(binding.avatarUser)
    }

    override fun shouldEnableBackPressed(): Boolean = !viewModel.getLoadingState()

    private fun setUpObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.txtLogOut.isVisible = state.isSignedIn
            binding.txtChangePassword.isVisible = state.isFirebaseAccount
            binding.materialDivider2.isVisible = state.isFirebaseAccount
            bindProfile(state.userProfile)
            onLoading(state.isLoading)
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}