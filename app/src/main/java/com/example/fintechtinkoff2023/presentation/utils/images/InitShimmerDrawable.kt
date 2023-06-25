package com.example.fintechtinkoff2023.presentation.utils.images

import android.graphics.Color
import androidx.annotation.ColorInt
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.wrappers.ManageResource
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

interface InitShimmerDrawable {
    fun map() : ShimmerDrawable
    class Base(private val highlightColor : Int) : InitShimmerDrawable{
        override fun map(): ShimmerDrawable {
            val shimmer = Shimmer.ColorHighlightBuilder()
                .setDuration(1000)
                .setBaseAlpha(1f)
                .setHighlightAlpha(0.5f)
                .setWidthRatio(1.5f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .setBaseColor(Color.WHITE)
                .setHighlightColor(highlightColor)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            return shimmerDrawable
        }
    }
}