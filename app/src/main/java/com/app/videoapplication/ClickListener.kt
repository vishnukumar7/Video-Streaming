package com.app.videoapplication

import androidx.annotation.ColorInt

interface NextPageListener {
    fun onNextPageCall(position: Int)
}

interface ClickViewAllListener{
    fun viewAll(position: Int,name: String)
}

interface ConfigurationListener {
    fun onMaskColorChanged(@ColorInt value: Int)
    fun onMaskCornerRadiusChanged(value: Float)
    fun onShowShimmerChanged(value: Boolean)
    fun onShimmerColorChanged(@ColorInt value: Int)
    fun onShimmerDurationChanged(value: Long)
    fun onShimmerDirectionChanged(value: Int)
    fun onShimmerAngleChanged(value: Int)
}