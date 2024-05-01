package com.kuro.movie.presenter.settings.change_profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentChangeProfileBinding
import com.kuro.movie.extension.getResultLauncher
import com.kuro.movie.extension.toPx
import com.kuro.movie.util.Constants.AVATAR_SIZE
import dagger.hilt.android.AndroidEntryPoint
import kuro.pratice.imagepicker.presenter.ImagePickerActivity
import kuro.pratice.imagepicker.utils.readUriListFromFile

@AndroidEntryPoint
class ChangeProfileFragment : BaseFragment<FragmentChangeProfileBinding>(
    inflater = FragmentChangeProfileBinding::inflate
) {

    private var imageUri: Uri? = null

    private val getUriContent = getResultLauncher { resultCode, _ ->
        if (resultCode == Activity.RESULT_OK) {
            imageUri = readUriListFromFile(requireActivity()).first()

            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.avatar_place_holder)
                .transform(CenterCrop(), CircleCrop())
                .override(AVATAR_SIZE.toPx(requireContext()))

            Glide.with(this)
                .load(imageUri)
                .apply(requestOptions)
                .into(binding.avatarUser)
        }
    }

    private val viewModel: ChangeProfileViewModel by viewModels()

    override fun onInitialize() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.btnConfirm.setOnClickListener {
            viewModel.submit(
                displayName = binding.etDisplayName.text.toString(),
                photoUri = imageUri
            )
        }

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnChangeAvatar.setOnClickListener {
            getUriContent.launch(Intent(requireActivity(), ImagePickerActivity::class.java))
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
            binding.btnConfirm.isEnabled = !state.isLoading
            binding.btnNavigateUp.isEnabled = !state.isLoading
            binding.etDisplayName.isEnabled = !state.isLoading
            binding.btnChangeAvatar.isEnabled = !state.isLoading
            bindProfile(state.currentProfile)
        }
    }

    private fun bindProfile(user: FirebaseUser?) {
        if (user == null) return
        binding.etDisplayName.setText(user.displayName)

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
}