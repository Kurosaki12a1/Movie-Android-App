package com.kuro.movie.presenter.detail

import android.content.Intent
import android.net.Uri
import com.kuro.movie.core.BaseFragment
import com.kuro.movie.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(
    inflater = FragmentDetailBinding::inflate
) {
    override fun onInitialize() {

    }

    private fun intentToTMDBWebSite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}