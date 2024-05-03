package com.kuro.movie.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow

abstract class BaseFragment<VB : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (behavior != null) { behavior() }
                }
            })
    }

    fun navigateToFlow(navigationFlow: NavigateFlow) {
        (requireActivity() as? NavigationFlow)?.navigateToFlow(navigationFlow)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}