package com.kuro.movie.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout.Behavior
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.R
import com.kuro.movie.util.Constants

abstract class BaseFragment<VB : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var backPressedTime = 0L

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    abstract fun onInitialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitialize()
    }

    protected fun addOnBackPressedCallBack(
        behavior: (() -> Unit?)? = null
    ) {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (behavior != null) {
                    behavior()
                    return
                }

                // Checks if there are any fragments in the back stack. If yes, pops the top fragment.
                if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
                    findNavController().popBackStack()
                    return
                }

                // If back button is pressed twice within TIMEOUT_BACK_PRESS duration, exits the app.
                if (backPressedTime + Constants.TIMEOUT_BACK_PRESS > System.currentTimeMillis()) {
                    requireActivity().finish()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.press_again_to_exit),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                // Updates the time of the last back press.
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}