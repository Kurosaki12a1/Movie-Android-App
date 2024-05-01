package com.kuro.movie.extension

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

fun Fragment.getResultLauncher(
    onResult: (Int, Intent?) -> Unit
): ActivityResultLauncher<Intent> {
    val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onResult(result.resultCode, result.data)
        }
    return launcher
}