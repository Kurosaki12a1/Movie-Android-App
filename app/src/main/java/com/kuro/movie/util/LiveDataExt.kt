package com.kuro.movie.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

fun <T> Fragment.observerLiveData(
    liveData: LiveData<T>,
    action: suspend (T) -> Unit
) {
    return liveData.observe(viewLifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            action(it)
        }
    }
}
