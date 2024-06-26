package com.kuro.movie.presenter.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import coil.load
import com.kuro.movie.R
import com.kuro.movie.domain.model.WatchProviderItemInfo
import com.kuro.movie.util.ImageUtil

class BindWatchProvidersHelper(private val context: Context) {
    fun bind(
        listOfWatchProviderItem: List<WatchProviderItemInfo>,
        linearLayout: LinearLayout,
    ) {
        removeWatchProvidersInLayout(linearLayout)
        if (listOfWatchProviderItem.isEmpty()) {
            val image = inflateImage(context, linearLayout)
            image.setBackgroundResource(R.drawable.no_watch_provider)
            linearLayout.addView(image)
            return
        }

        for (item in listOfWatchProviderItem) {
            val image = inflateImage(context, linearLayout)
            image.load(
                ImageUtil.getImage(imageUrl = item.logoPath)
            )
            image.setOnClickListener {
                Toast.makeText(context, item.providerName, Toast.LENGTH_SHORT).show()
            }
            linearLayout.addView(image)
        }
    }

    private fun inflateImage(
        context: Context,
        linearLayout: LinearLayout,
    ): ImageView {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.watch_provider_image,
                linearLayout,
                false
            ) as ImageView
    }

    private fun removeWatchProvidersInLayout(linearLayout: LinearLayout) {
        if (linearLayout.childCount >= 1) {
            linearLayout.removeAllViewsInLayout()
        }
    }
}