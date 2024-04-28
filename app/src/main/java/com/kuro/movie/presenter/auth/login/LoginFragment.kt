package com.kuro.movie.presenter.auth.login

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.R
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentLoginBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    inflater = FragmentLoginBinding::inflate
) {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onInitialize() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        addOnBackPressedCallBack()
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.btnSignIn.setOnClickListener {
            loginViewModel.signIn(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
        }
    }

    private fun setUpObservers() {
        loginViewModel.snackBarMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        loginViewModel.uiState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    (requireActivity() as NavigationFlow).navigateToFlow(NavigateFlow.HomeFlow)
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
